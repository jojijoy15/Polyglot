package concurrency.ds;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentHashMapOpsTest {

    private ConcurrentHashMapOps ops;

    @BeforeEach
    void setUp() {
        ops = new ConcurrentHashMapOps();
    }

    // ==================== Basic operations ====================

    @Test
    void putAndGet() {
        ops.put("a", 1);
        assertEquals(1, ops.get("a"));
    }

    @Test
    void getReturnsNullForAbsentKey() {
        assertNull(ops.get("missing"));
    }

    @Test
    void removeDeletesEntry() {
        ops.put("a", 1);
        assertEquals(1, ops.remove("a"));
        assertNull(ops.get("a"));
    }

    @Test
    void containsKey() {
        ops.put("x", 10);
        assertTrue(ops.containsKey("x"));
        assertFalse(ops.containsKey("y"));
    }

    @Test
    void sizeReflectsEntries() {
        assertEquals(0, ops.size());
        ops.put("a", 1);
        ops.put("b", 2);
        assertEquals(2, ops.size());
    }

    // ==================== Atomic compound operations ====================

    @Test
    void putIfAbsent_insertsWhenAbsent() {
        assertNull(ops.putIfAbsent("a", 1)); // returns null → inserted
        assertEquals(1, ops.get("a"));
    }

    @Test
    void putIfAbsent_doesNotOverwriteExisting() {
        ops.put("a", 1);
        assertEquals(1, ops.putIfAbsent("a", 99)); // returns existing value
        assertEquals(1, ops.get("a")); // unchanged
    }

    @Test
    void computeIfAbsent_computesForNewKey() {
        // key "hello" → length 5
        assertEquals(5, ops.computeIfAbsent("hello"));
    }

    @Test
    void computeIfAbsent_doesNotRecomputeForExistingKey() {
        ops.put("hello", 42);
        assertEquals(42, ops.computeIfAbsent("hello")); // keeps existing
    }

    @Test
    void computeIfPresent_incrementsExistingValue() {
        ops.put("counter", 10);
        assertEquals(11, ops.computeIfPresent("counter"));
    }

    @Test
    void computeIfPresent_returnsNullWhenKeyAbsent() {
        assertNull(ops.computeIfPresent("absent"));
    }

    @Test
    void compute_createsNewEntryWhenAbsent() {
        assertEquals(5, ops.compute("a", 5));
    }

    @Test
    void compute_addsToExistingValue() {
        ops.put("a", 10);
        assertEquals(13, ops.compute("a", 3));
    }

    @Test
    void merge_insertsWhenAbsent() {
        assertEquals(5, ops.merge("word", 5));
    }

    @Test
    void merge_sumsWithExistingValue() {
        ops.put("word", 5);
        assertEquals(8, ops.merge("word", 3));
    }

    @Test
    void replace_replacesExistingValue() {
        ops.put("a", 1);
        assertEquals(1, ops.replace("a", 99));
        assertEquals(99, ops.get("a"));
    }

    @Test
    void replace_returnsNullWhenKeyAbsent() {
        assertNull(ops.replace("missing", 99));
    }

    @Test
    void replaceIfEqual_swapsOnMatch() {
        ops.put("a", 1);
        assertTrue(ops.replaceIfEqual("a", 1, 2));
        assertEquals(2, ops.get("a"));
    }

    @Test
    void replaceIfEqual_doesNotSwapOnMismatch() {
        ops.put("a", 1);
        assertFalse(ops.replaceIfEqual("a", 999, 2));
        assertEquals(1, ops.get("a"));
    }

    // ==================== Bulk operations ====================

    @Test
    void reduceValues_sumsAllValues() {
        ops.put("a", 10);
        ops.put("b", 20);
        ops.put("c", 30);
        assertEquals(60, ops.reduceValues());
    }

    @Test
    void searchForValueGreaterThan_findsMatch() {
        ops.put("low", 5);
        ops.put("high", 100);
        String found = ops.searchForValueGreaterThan(50);
        assertEquals("high", found);
    }

    @Test
    void searchForValueGreaterThan_returnsNullWhenNoMatch() {
        ops.put("low", 5);
        assertNull(ops.searchForValueGreaterThan(50));
    }

    // ==================== Concurrent write safety ====================

    @Test
    void concurrentMerge_wordCounting() throws InterruptedException {
        String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < words.length / 2; i++) ops.merge(words[i], 1);
        });
        Thread t2 = new Thread(() -> {
            for (int i = words.length / 2; i < words.length; i++) ops.merge(words[i], 1);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(3, ops.get("apple"));
        assertEquals(2, ops.get("banana"));
        assertEquals(1, ops.get("cherry"));
    }
}

