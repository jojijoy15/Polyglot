package com.problems.learning.ds.design.lru;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

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

    public LRUCache(int capacity) {
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


    /*
       head[null|null|A] -> A[null|1|B] -> B[A|2|C] -> C[B|3|D] -> D[C|4|null] -> tail[D|null|null]

       Remove C node
        Ops 1 : C's Prev's Next : C[B.next] => D
        Ops 2 : C's Next's Prev : C[D.prev] => B
     */
    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /*
       head[null|null|A] -> A[null|1|B] -> B[A|2|C] -> C[B|3|D] -> tail[D|null|null]

       Add at tail
       D[null|4|null]

        Ops 1 : Copy tail's prev to new node's prev
            D[prev] = tail.prev
        Ops 2 : Make new node's next to tail
            D[next] = tail
        Ops 3 : Make tail's prev's next i.e., second last node point to new Node
            tail.prev.next = D
        Ops 4 : tail's prev is new node
            tail.prev = D
     */
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

