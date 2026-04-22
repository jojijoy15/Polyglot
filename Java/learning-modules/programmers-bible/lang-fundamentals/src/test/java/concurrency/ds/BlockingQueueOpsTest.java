package concurrency.ds;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BlockingQueueOpsTest {

    private final BlockingQueueOps ops = new BlockingQueueOps();

    // ==================== Queue creation ====================

    @Test
    void createArrayBlockingQueue_hasBoundedCapacity() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        assertEquals(3, q.remainingCapacity());
    }

    @Test
    void createLinkedBlockingQueue_hasBoundedCapacity() {
        BlockingQueue<String> q = ops.createLinkedBlockingQueue(5);
        assertEquals(5, q.remainingCapacity());
    }

    @Test
    void createUnboundedLinkedBlockingQueue_hasMaxCapacity() {
        BlockingQueue<String> q = ops.createUnboundedLinkedBlockingQueue();
        assertEquals(Integer.MAX_VALUE, q.remainingCapacity());
    }

    // ==================== Insert operations ====================

    @Test
    void add_insertsElement() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        assertTrue(ops.add(q, "A"));
        assertEquals(1, q.size());
    }

    @Test
    void add_throwsWhenFull() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(1);
        ops.add(q, "A");
        assertThrows(IllegalStateException.class, () -> ops.add(q, "B"));
    }

    @Test
    void offer_returnsFalseWhenFull() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(1);
        assertTrue(ops.offer(q, "A"));
        assertFalse(ops.offer(q, "B")); // no exception, just false
    }

    @Test
    void put_blocksUntilSpaceAvailable() throws InterruptedException {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(1);
        ops.put(q, "A");

        // put("B") will block because the queue is full.
        // A consumer thread frees space after a short delay.
        Thread consumer = new Thread(() -> {
            try { Thread.sleep(100); q.take(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        consumer.start();

        ops.put(q, "B"); // should unblock after consumer takes "A"
        consumer.join();

        assertEquals("B", q.peek());
    }

    @Test
    void offerWithTimeout_returnsFalseOnTimeout() throws InterruptedException {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(1);
        ops.offer(q, "A");
        assertFalse(ops.offerWithTimeout(q, "B", 50, TimeUnit.MILLISECONDS));
    }

    // ==================== Remove operations ====================

    @Test
    void remove_throwsWhenEmpty() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        assertThrows(NoSuchElementException.class, () -> ops.remove(q));
    }

    @Test
    void poll_returnsNullWhenEmpty() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        assertNull(ops.poll(q));
    }

    @Test
    void poll_returnsHead() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        ops.add(q, "A");
        ops.add(q, "B");
        assertEquals("A", ops.poll(q)); // FIFO
    }

    @Test
    void take_blocksUntilElementAvailable() throws InterruptedException {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);

        // Producer adds an element after a short delay
        Thread producer = new Thread(() -> {
            try { Thread.sleep(100); q.put("Hello"); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        producer.start();

        String item = ops.take(q); // blocks until producer puts "Hello"
        producer.join();

        assertEquals("Hello", item);
    }

    @Test
    void pollWithTimeout_returnsNullOnTimeout() throws InterruptedException {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        assertNull(ops.pollWithTimeout(q, 50, TimeUnit.MILLISECONDS));
    }

    // ==================== Examine operations ====================

    @Test
    void element_throwsWhenEmpty() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        assertThrows(NoSuchElementException.class, () -> ops.element(q));
    }

    @Test
    void element_returnsHeadWithoutRemoving() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        ops.add(q, "A");
        assertEquals("A", ops.element(q));
        assertEquals(1, q.size()); // still there
    }

    @Test
    void peek_returnsNullWhenEmpty() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        assertNull(ops.peek(q));
    }

    @Test
    void peek_returnsHeadWithoutRemoving() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        ops.add(q, "X");
        assertEquals("X", ops.peek(q));
        assertEquals(1, q.size());
    }

    // ==================== Bulk operations ====================

    @Test
    void drainAll_removesAllElements() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(5);
        ops.add(q, "A");
        ops.add(q, "B");
        ops.add(q, "C");

        List<String> drained = ops.drainAll(q);

        assertEquals(List.of("A", "B", "C"), drained);
        assertEquals(0, q.size());
    }

    @Test
    void drainAtMost_limitsCount() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(5);
        ops.add(q, "A");
        ops.add(q, "B");
        ops.add(q, "C");

        List<String> drained = ops.drainAtMost(q, 2);

        assertEquals(List.of("A", "B"), drained);
        assertEquals(1, q.size()); // "C" remains
    }

    @Test
    void remainingCapacity_decreasesWithInsertions() {
        BlockingQueue<String> q = ops.createArrayBlockingQueue(3);
        assertEquals(3, ops.remainingCapacity(q));
        ops.add(q, "A");
        assertEquals(2, ops.remainingCapacity(q));
    }

    // ==================== Producer-Consumer ====================

    @Test
    void producerConsumerDemo_allItemsConsumedInOrder() throws InterruptedException {
        List<String> consumed = ops.producerConsumerDemo(10, 3);

        List<String> expected = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> "Item-" + i)
                .collect(Collectors.toList());

        assertEquals(expected, consumed);
    }

    @Test
    void producerConsumerDemo_worksWithSingleCapacity() throws InterruptedException {
        List<String> consumed = ops.producerConsumerDemo(5, 1);
        assertEquals(5, consumed.size());
    }
}

