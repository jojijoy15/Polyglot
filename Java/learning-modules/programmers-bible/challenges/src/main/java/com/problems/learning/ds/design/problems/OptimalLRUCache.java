package com.problems.learning.ds.design.problems;

import java.util.HashMap;
import java.util.Map;

public class OptimalLRUCache<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head; // sentinel - before LRU
    private final Node<K, V> tail; // sentinel - after MRU

    public OptimalLRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();

        // Initialize sentinel nodes
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        // Move to most recently used position
        removeNode(node);
        addAtTail(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> existing = cache.get(key);

        if (existing != null) {
            // Update existing node
            existing.value = value;
            removeNode(existing);
            addAtTail(existing);
        } else {
            // Evict if at capacity
            if (cache.size() == capacity) {
                Node<K, V> lru = head.next;
                removeNode(lru);
                cache.remove(lru.key);
            }
            // Insert new node
            Node<K, V> newNode = new Node<>(key, value);
            addAtTail(newNode);
            cache.put(key, newNode);
        }
    }


    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addAtTail(Node<K, V> node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }

    public int size() {
        return cache.size();
    }
}

