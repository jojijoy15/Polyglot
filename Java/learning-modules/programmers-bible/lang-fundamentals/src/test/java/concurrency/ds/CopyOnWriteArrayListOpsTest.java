package concurrency.ds;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CopyOnWriteArrayListOpsTest {

    private CopyOnWriteArrayListOps ops;

    @BeforeEach
    void setUp() {
        ops = new CopyOnWriteArrayListOps();
    }

    // ==================== Write operations ====================

    @Test
    void add_appendsElement() {
        ops.add("A");
        ops.add("B");
        assertEquals(2, ops.size());
        assertEquals("A", ops.get(0));
        assertEquals("B", ops.get(1));
    }

    @Test
    void addAtIndex_insertsAtPosition() {
        ops.add("A");
        ops.add("C");
        ops.addAtIndex(1, "B");
        assertEquals(List.of("A", "B", "C"), ops.snapshot());
    }

    @Test
    void addIfAbsent_addsOnlyWhenMissing() {
        assertTrue(ops.addIfAbsent("A"));
        assertFalse(ops.addIfAbsent("A")); // already present
        assertEquals(1, ops.size());
    }

    @Test
    void set_replacesElement() {
        ops.add("A");
        String old = ops.set(0, "Z");
        assertEquals("A", old);
        assertEquals("Z", ops.get(0));
    }

    @Test
    void removeAtIndex_removesCorrectElement() {
        ops.add("A");
        ops.add("B");
        ops.add("C");
        String removed = ops.removeAtIndex(1);
        assertEquals("B", removed);
        assertEquals(List.of("A", "C"), ops.snapshot());
    }

    @Test
    void remove_removesFirstOccurrence() {
        ops.add("A");
        ops.add("B");
        ops.add("A");
        assertTrue(ops.remove("A"));
        assertEquals(List.of("B", "A"), ops.snapshot());
    }

    // ==================== Read operations ====================

    @Test
    void contains_findsExistingElement() {
        ops.add("X");
        assertTrue(ops.contains("X"));
        assertFalse(ops.contains("Y"));
    }

    @Test
    void indexOf_returnsCorrectPosition() {
        ops.add("A");
        ops.add("B");
        ops.add("C");
        assertEquals(1, ops.indexOf("B"));
        assertEquals(-1, ops.indexOf("Z"));
    }

    // ==================== Snapshot iteration ====================

    @Test
    void snapshot_isImmutableCopy() {
        ops.add("A");
        ops.add("B");
        List<String> snap = ops.snapshot();
        ops.add("C"); // mutate after snapshot
        assertEquals(List.of("A", "B"), snap); // snapshot unchanged
        assertEquals(3, ops.size());            // original list has 3
    }

    @Test
    void iterator_doesNotThrowConcurrentModificationException() {
        ops.add("A");
        ops.add("B");
        ops.add("C");

        // Iterate and modify concurrently — should NOT throw
        assertDoesNotThrow(() -> {
            Iterator<String> it = ops.getList().iterator();
            while (it.hasNext()) {
                it.next();
                ops.add("NEW"); // mutation during iteration is safe
            }
        });
    }

    @Test
    void iterator_removethrowsUnsupportedOperationException() {
        ops.add("A");
        Iterator<String> it = ops.getList().iterator();
        it.next();
        // Snapshot iterators do not support remove()
        assertThrows(UnsupportedOperationException.class, it::remove);
    }

    @Test
    void iterateWhileMutating_readerSeesSnapshotOnly() throws InterruptedException {
        List<String> seen = ops.iterateWhileMutating();
        // Reader started with [A, B, C]; writer added D while reader was iterating.
        // Snapshot iterator should only see [A, B, C].
        assertEquals(List.of("A", "B", "C"), seen);
    }

    // ==================== Concurrent safety ====================

    @Test
    void concurrentAdds_allElementsPresent() throws InterruptedException {
        Thread t1 = new Thread(() -> { for (int i = 0; i < 100; i++) ops.add("T1-" + i); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 100; i++) ops.add("T2-" + i); });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(200, ops.size());
    }
}

