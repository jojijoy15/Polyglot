package com.problems.learning.ds.design.problems;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class LRUCacheTest {

    LRUCache<String, Integer> cache = new LRUCache<>(3);

    @Test
    void putTillCapacity()  {
        cache.put("one", 1);
        cache.put("two", 2);
        cache.put("three", 3);
        cache.put("four", 4);

        assertThat(cache.get("one")).isNull();
        checkAccessNodes("one");
    }

    private void checkAccessNodes(String key) {
        try {
            Field accessNodes = cache.getClass().getDeclaredField("accessNodes");
            accessNodes.setAccessible(true);
            LRUCacheLinkedList node  = (LRUCacheLinkedList) accessNodes.get(cache);
            Field head = node.getClass().getDeclaredField("head");
            head.setAccessible(true);
            LRUCacheLinkedList.Node<String> curr = (LRUCacheLinkedList.Node<String>) head.get(node);
            while(curr != null) {
                String nodeKey = curr.data;
                assertThat(nodeKey).isNotEqualTo(key);
                curr = curr.next;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void get() {

        cache.put("one", 1);
        cache.put("two", 2);
        cache.get("one");
        cache.put("three", 3);
        cache.put("four", 4);

        assertThat(cache.get("two")).isNull();
        checkAccessNodes("two");
    }
}