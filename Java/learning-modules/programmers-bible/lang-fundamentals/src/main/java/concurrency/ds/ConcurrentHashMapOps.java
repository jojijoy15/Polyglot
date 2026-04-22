package concurrency.ds;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demonstrates key operations of {@link ConcurrentHashMap}.
 * <p>
 * Thread-safety characteristics:
 * <ul>
 *   <li>Lock-free reads – get(), containsKey(), size() never block.</li>
 *   <li>Fine-grained locking on writes – only the bucket (segment) being modified is locked,
 *       so multiple threads can write to different buckets concurrently.</li>
 *   <li>Atomic compound operations – putIfAbsent, compute, merge, replace are single atomic steps,
 *       eliminating the classic "check-then-act" race condition.</li>
 *   <li>Weakly consistent iterators – never throw ConcurrentModificationException;
 *       they reflect the state of the map at (or sometime after) iterator creation.</li>
 *   <li>Does NOT allow null keys or null values (unlike HashMap).</li>
 * </ul>
 */
public class ConcurrentHashMapOps {

    private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    // ==================== Basic operations (lock-free reads, fine-grained write locks) ====================

    /** put() – Inserts or replaces a key-value pair. Locks only the target bucket. */
    public Integer put(String key, int value) {
        return map.put(key, value);
    }

    /** get() – Lock-free read; returns null if key absent. */
    public Integer get(String key) {
        return map.get(key);
    }

    /** remove() – Atomically removes the entry for the given key. */
    public Integer remove(String key) {
        return map.remove(key);
    }

    /** containsKey() – Lock-free read. */
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    /** size() – Returns an estimate; fully accurate only when no concurrent modifications are happening. */
    public int size() {
        return map.size();
    }

    // ==================== Atomic compound operations (no external synchronization needed) ====================

    /**
     * putIfAbsent() – Inserts only if the key is not already present.
     * Avoids the classic race:
     *   if (!map.containsKey(k)) map.put(k, v);  // NOT thread-safe with regular HashMap
     *
     * Returns null if the key was absent (and insertion happened), or the existing value.
     */
    public Integer putIfAbsent(String key, int value) {
        return map.putIfAbsent(key, value);
    }

    /**
     * computeIfAbsent() – Computes a value using the mapping function only if key is absent.
     * Useful for lazy initialization of values.
     * The mapping function runs atomically – no two threads will compute for the same key.
     */
    public Integer computeIfAbsent(String key) {
        return map.computeIfAbsent(key, k -> k.length());
    }

    /**
     * computeIfPresent() – Re-computes a value only if key is already present.
     * Example: increment a counter atomically.
     */
    public Integer computeIfPresent(String key) {
        return map.computeIfPresent(key, (k, currentValue) -> currentValue + 1);
    }

    /**
     * compute() – Atomically computes a new value for a key (whether present or not).
     * If the remapping function returns null, the entry is removed.
     */
    public Integer compute(String key, int delta) {
        return map.compute(key, (k, currentValue) -> (currentValue == null ? 0 : currentValue) + delta);
    }

    /**
     * merge() – Atomically merges a new value with an existing value.
     * If key is absent, inserts the new value directly.
     * If key is present, applies the remapping function to combine old and new values.
     * Classic use case: word counting.
     */
    public Integer merge(String key, int value) {
        return map.merge(key, value, Integer::sum);
    }

    /**
     * replace() – Atomically replaces the value only if the key is already mapped.
     * Returns the old value, or null if key was absent.
     */
    public Integer replace(String key, int newValue) {
        return map.replace(key, newValue);
    }

    /**
     * replace(key, expectedOld, newValue) – CAS-style (Compare-And-Swap) replace.
     * Replaces only if the current value equals expectedOld.
     * Returns true if the replacement was made.
     */
    public boolean replaceIfEqual(String key, int expectedOld, int newValue) {
        return map.replace(key, expectedOld, newValue);
    }

    // ==================== Bulk / aggregate operations (parallel-friendly) ====================

    /**
     * forEach() with parallelism threshold – processes entries in parallel if the map
     * size exceeds the threshold. Uses ForkJoinPool.commonPool() under the hood.
     * Threshold of 1 = always parallel; Long.MAX_VALUE = always sequential.
     */
    public long parallelSum(long parallelismThreshold) {
        long[] sum = {0};
        map.forEach(parallelismThreshold, (key, value) -> {
            synchronized (sum) { // sum array is shared
                sum[0] += value;
            }
        });
        return sum[0];
    }

    /**
     * search() – Searches entries in parallel, returns the first non-null result
     * from the search function. Short-circuits once a result is found.
     */
    public String searchForValueGreaterThan(int threshold) {
        return map.search(1, (key, value) -> value > threshold ? key : null);
    }

    /**
     * reduce() – Parallel reduction across all entries.
     * Example: sum all values.
     */
    public int reduceValues() {
        return map.reduceValues(1, Integer::sum);
    }

    /** Returns a snapshot of the internal map for testing. */
    public Map<String, Integer> getMap() {
        return map;
    }
}

