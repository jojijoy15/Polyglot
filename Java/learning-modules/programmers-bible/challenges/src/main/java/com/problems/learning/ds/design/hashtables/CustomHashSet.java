package com.problems.learning.ds.design.hashtables;


public class CustomHashSet<K> {

    private static class Node<K> {
        K key;
        Node<K> next;

        Node(K key, Node<K> next) {
            this.key = key;
            this.next = next;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public CustomHashSet() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public CustomHashSet(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        buckets = new Node[initialCapacity];
        size = 0;
    }


    public boolean add(K key) {
        if (contains(key)) {
            return false;
        }

        if (size >= buckets.length * LOAD_FACTOR) {
            resize();
        }

        int index = getBucketIndex(key);
        // Insert at head of the chain
        buckets[index] = new Node<>(key, buckets[index]);
        size++;
        return true;
    }


    public boolean contains(K key) {
        int index = getBucketIndex(key);
        Node<K> current = buckets[index];

        while (current != null) {
            if (keysEqual(current.key, key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    public boolean remove(K key) {
        int index = getBucketIndex(key);
        Node<K> current = buckets[index];
        Node<K> prev = null;

        while (current != null) {
            if (keysEqual(current.key, key)) {
                if (prev == null) {
                    // Removing head of the chain
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }


    public int size() {
        return size;
    }

    /**
     * Returns true if this set contains no elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }


    private int getBucketIndex(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        // Spread higher bits into lower bits (same technique as java.util.HashMap)
        hash ^= (hash >>> 16);
        return hash & (buckets.length - 1);
    }


    private boolean keysEqual(K a, K b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }


    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];

        for (Node<K> head : oldBuckets) {
            Node<K> current = head;
            while (current != null) {
                Node<K> next = current.next;
                int newIndex = getBucketIndex(current.key);
                current.next = buckets[newIndex];
                buckets[newIndex] = current;
                current = next;
            }
        }
    }
}

