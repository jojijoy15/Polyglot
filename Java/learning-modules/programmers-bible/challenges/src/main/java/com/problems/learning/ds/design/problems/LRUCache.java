package com.problems.learning.ds.design.problems;

import java.util.HashMap;

public class LRUCache<K, V> {

    private HashMap<K, V> cache =  new HashMap<>();
    private final LRUCacheLinkedList<K> accessNodes = new LRUCacheLinkedList<>();
    private final int capacity;
    int currentSize = 0;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(K key, V value) {
        if(!cache.containsKey(key)) {
            cache.put(key, value);
            accessNodes.addAtEnd(key);
            currentSize++;
        } else {
            accessNodes.remove(key);
            accessNodes.addAtEnd(key);
            cache.put(key, value);
        }
        if(currentSize >= capacity) {
            K nodeData = accessNodes.removeFirst();
            cache.remove(nodeData);
            currentSize--;
        }

    }

    public V get(K key) {
        if(cache.containsKey(key)) {
            accessNodes.remove(key);
            accessNodes.addAtEnd(key);
            return cache.get(key);
        }
        return null;
    }

}
