package com.problems.learning.ds.design.lru;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * LRU Cache with TTL-based expiration and latency tracking.
 * <p>
 * Features:
 * - O(1) get/put via HashMap + Doubly Linked List
 * - Per-entry TTL: entries auto-expire after a configurable duration
 * - Background eviction thread cleans up expired entries
 * - Per-operation latency (nanoseconds) recorded and retrievable
 * - Thread-safe via ReentrantReadWriteLock
 *
 * @param <K> key type
 * @param <V> value type
 */
public class LRUCacheTimer<K, V> {

    // ─────────────────────────────────────────────
    // Inner: Cache Node (doubly linked list node)
    // ─────────────────────────────────────────────
    private static class Node<K, V> {
        K key;
        V value;
        long expiryTimeMs;          // absolute epoch ms when this entry expires
        Node<K, V> prev, next;

        Node(K key, V value, long ttlMs) {
            this.key = key;
            this.value = value;
            this.expiryTimeMs = System.currentTimeMillis() + ttlMs;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTimeMs;
        }
    }

    // ─────────────────────────────────────────────
    // Inner: Latency Stats per operation
    // ─────────────────────────────────────────────
    public static class LatencyStats {
        private final List<Long> samples = new ArrayList<>();

        void record(long nanos) {
            samples.add(nanos);
        }

        public long lastNanos() {
            return samples.isEmpty() ? 0 : samples.get(samples.size() - 1);
        }

        public double avgNanos() {
            return samples.stream().mapToLong(l -> l).average().orElse(0);
        }

        public long maxNanos() {
            return samples.stream().mapToLong(l -> l).max().orElse(0);
        }

        public long minNanos() {
            return samples.stream().mapToLong(l -> l).min().orElse(0);
        }

        public int sampleCount() {
            return samples.size();
        }

        @Override
        public String toString() {
            return String.format(
                    "samples=%d | last=%.3fµs | avg=%.3fµs | min=%.3fµs | max=%.3fµs",
                    sampleCount(),
                    lastNanos() / 1_000.0,
                    avgNanos() / 1_000.0,
                    minNanos() / 1_000.0,
                    maxNanos() / 1_000.0
            );
        }
    }

    // ─────────────────────────────────────────────
    // Fields
    // ─────────────────────────────────────────────
    private final int capacity;
    private final long defaultTtlMs;
    private final Map<K, Node<K, V>> map;

    // Sentinel head/tail (never hold real data)
    private final Node<K, V> head, tail;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final LatencyStats getStats = new LatencyStats();
    private final LatencyStats putStats = new LatencyStats();
    private final LatencyStats removeStats = new LatencyStats();

    private int hitCount = 0;
    private int missCount = 0;
    private int evictionCount = 0;

    private final ScheduledExecutorService cleaner =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "lru-cache-cleaner");
                t.setDaemon(true);
                return t;
            });

    // ─────────────────────────────────────────────
    // Constructor
    // ─────────────────────────────────────────────

    /**
     * @param capacity        maximum number of entries before LRU eviction kicks in
     * @param defaultTtlMs    default time-to-live in milliseconds for each entry
     * @param cleanIntervalMs how often the background cleaner runs (ms)
     */
    public LRUCacheTimer(int capacity, long defaultTtlMs, long cleanIntervalMs) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.capacity = capacity;
        this.defaultTtlMs = defaultTtlMs;
        this.map = new HashMap<>(capacity);

        head = new Node<>(null, null, Long.MAX_VALUE);
        tail = new Node<>(null, null, Long.MAX_VALUE);
        head.next = tail;
        tail.prev = head;

        // Schedule background TTL cleanup
        cleaner.scheduleAtFixedRate(
                this::evictExpired,
                cleanIntervalMs,
                cleanIntervalMs,
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * Convenience constructor: 1-second TTL, cleanup every 500 ms
     */
    public LRUCacheTimer(int capacity) {
        this(capacity, 1_000, 500);
    }

    // ─────────────────────────────────────────────
    // Public API
    // ─────────────────────────────────────────────

    /**
     * Get value for key; returns null on miss or expired entry.
     */
    public V get(K key) {
        long start = System.nanoTime();
        try {
            lock.writeLock().lock();   // write because we move node to front
            Node<K, V> node = map.get(key);

            if (node == null) {
                missCount++;
                return null;
            }
            if (node.isExpired()) {
                removeNode(node);
                map.remove(key);
                missCount++;
                evictionCount++;
                return null;
            }
            // Move to front (most-recently-used)
            moveToFront(node);
            hitCount++;
            return node.value;
        } finally {
            getStats.record(System.nanoTime() - start);
            lock.writeLock().unlock();
        }
    }

    /**
     * Put with the default TTL.
     */
    public void put(K key, V value) {
        put(key, value, defaultTtlMs);
    }

    /**
     * Put with a custom TTL (overrides default for this entry).
     */
    public void put(K key, V value, long ttlMs) {
        long start = System.nanoTime();
        try {
            lock.writeLock().lock();
            Node<K, V> existing = map.get(key);
            if (existing != null) {
                // Update value + TTL, move to front
                existing.value = value;
                existing.expiryTimeMs = System.currentTimeMillis() + ttlMs;
                moveToFront(existing);
            } else {
                Node<K, V> newNode = new Node<>(key, value, ttlMs);
                map.put(key, newNode);
                addToFront(newNode);

                if (map.size() > capacity) {
                    // Evict LRU (node before tail)
                    Node<K, V> lru = tail.prev;
                    if (lru != head) {
                        removeNode(lru);
                        map.remove(lru.key);
                        evictionCount++;
                    }
                }
            }
        } finally {
            putStats.record(System.nanoTime() - start);
            lock.writeLock().unlock();
        }
    }

    /**
     * Explicitly remove a key. Returns true if it was present.
     */
    public boolean remove(K key) {
        long start = System.nanoTime();
        try {
            lock.writeLock().lock();
            Node<K, V> node = map.remove(key);
            if (node != null) {
                removeNode(node);
                return true;
            }
            return false;
        } finally {
            removeStats.record(System.nanoTime() - start);
            lock.writeLock().unlock();
        }
    }

    /**
     * Number of non-expired entries currently in the cache.
     */
    public int size() {
        lock.readLock().lock();
        try {
            return map.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Gracefully shut down the background cleaner thread.
     */
    public void shutdown() {
        cleaner.shutdown();
    }

    // ─────────────────────────────────────────────
    // Stats & Diagnostics
    // ─────────────────────────────────────────────
    public LatencyStats getLatency() {
        return getStats;
    }

    public LatencyStats putLatency() {
        return putStats;
    }

    public LatencyStats removeLatency() {
        return removeStats;
    }

    public int hitCount() {
        return hitCount;
    }

    public int missCount() {
        return missCount;
    }

    public int evictionCount() {
        return evictionCount;
    }

    public double hitRate() {
        int total = hitCount + missCount;
        return total == 0 ? 0 : (double) hitCount / total * 100;
    }

    public void printStats() {
        System.out.println("══════════════ LRU Cache Stats ══════════════");
        System.out.printf("  Capacity  : %d | Size: %d%n", capacity, size());
        System.out.printf("  Hits      : %d | Misses: %d | Hit Rate: %.1f%%%n",
                hitCount, missCount, hitRate());
        System.out.printf("  Evictions : %d%n", evictionCount);
        System.out.println("  GET    latency → " + getStats);
        System.out.println("  PUT    latency → " + putStats);
        System.out.println("  REMOVE latency → " + removeStats);
        System.out.println("═════════════════════════════════════════════");
    }

    /**
     * Snapshot of keys from MRU → LRU order (for debugging).
     */
    public List<K> keysInOrder() {
        lock.readLock().lock();
        try {
            List<K> result = new ArrayList<>();
            Node<K, V> cur = head.next;
            while (cur != tail) {
                result.add(cur.key);
                cur = cur.next;
            }
            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    // ─────────────────────────────────────────────
    // Private helpers
    // ─────────────────────────────────────────────

    /**
     * Called by the background cleaner thread.
     */
    private void evictExpired() {
        lock.writeLock().lock();
        try {
            Node<K, V> cur = head.next;
            while (cur != tail) {
                Node<K, V> next = cur.next;
                if (cur.isExpired()) {
                    removeNode(cur);
                    map.remove(cur.key);
                    evictionCount++;
                }
                cur = next;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void addToFront(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToFront(Node<K, V> node) {
        removeNode(node);
        addToFront(node);
    }

    // ─────────────────────────────────────────────
    // Demo main
    // ─────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        System.out.println("▶ Creating LRU cache: capacity=3, TTL=2s, cleanup every 500ms\n");
        LRUCacheTimer<String, String> cache = new LRUCacheTimer<>(3, 2_000, 500);

        // --- Basic LRU eviction ---
        System.out.println("── PUT A, B, C ──");
        cache.put("A", "Apple");
        cache.put("B", "Banana");
        cache.put("C", "Cherry");
        System.out.println("Order (MRU→LRU): " + cache.keysInOrder());  // C B A

        System.out.println("\n── GET A  (makes A most-recently-used) ──");
        System.out.println("A → " + cache.get("A"));
        System.out.println("Order: " + cache.keysInOrder());            // A C B

        System.out.println("\n── PUT D  (evicts LRU = B) ──");
        cache.put("D", "Date");
        System.out.println("Order: " + cache.keysInOrder());            // D A C
        System.out.println("B → " + cache.get("B"));                   // null (evicted)

        // --- TTL / per-entry expiry ---
        System.out.println("\n── PUT E with custom TTL=1s ──");
        cache.put("E", "Elderberry", 1_000);
        System.out.println("E (fresh) → " + cache.get("E"));           // Elderberry

        System.out.println("  Sleeping 1.2 seconds...");
        Thread.sleep(1_200);
        System.out.println("E (after TTL) → " + cache.get("E"));       // null

        // --- Explicit remove ---
        System.out.println("\n── Explicit remove of A ──");
        cache.remove("A");
        System.out.println("A → " + cache.get("A"));                   // null

        // --- Background cleanup: wait for default 2s TTL ---
        System.out.println("\n── Waiting 2.5s for remaining entries to expire... ──");
        Thread.sleep(2_500);
        System.out.println("Cache size after expiry: " + cache.size()); // 0

        // --- Print final stats ---
        System.out.println();
        cache.printStats();

        cache.shutdown();
    }
}