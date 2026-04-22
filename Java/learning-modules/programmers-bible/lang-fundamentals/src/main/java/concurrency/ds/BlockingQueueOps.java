package concurrency.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates key operations of {@link BlockingQueue} using both
 * {@link ArrayBlockingQueue} (bounded, array-backed) and {@link LinkedBlockingQueue} (optionally bounded, node-backed).
 * <p>
 * Thread-safety characteristics:
 * <ul>
 *   <li>All operations are thread-safe — internally uses ReentrantLock(s).</li>
 *   <li>ArrayBlockingQueue uses a <b>single lock</b> for both put and take.</li>
 *   <li>LinkedBlockingQueue uses <b>two separate locks</b> (putLock + takeLock), allowing
 *       concurrent put and take operations.</li>
 *   <li>Designed for the <b>Producer-Consumer</b> pattern.</li>
 * </ul>
 * <p>
 * Four families of operations:
 * <pre>
 *  ┌──────────────┬────────────┬─────────────┬──────────────────┬───────────────┐
 *  │              │ Throws     │ Returns     │ Blocks           │ Times out     │
 *  │              │ Exception  │ Special Val │                  │               │
 *  ├──────────────┼────────────┼─────────────┼──────────────────┼───────────────┤
 *  │ Insert       │ add(e)     │ offer(e)    │ put(e)           │ offer(e,t,u)  │
 *  │ Remove       │ remove()   │ poll()      │ take()           │ poll(t,u)     │
 *  │ Examine      │ element()  │ peek()      │ —                │ —             │
 *  └──────────────┴────────────┴─────────────┴──────────────────┴───────────────┘
 * </pre>
 */
public class BlockingQueueOps {

    // ==================== Queue creation ====================

    /** Creates a bounded ArrayBlockingQueue with the given capacity. */
    public BlockingQueue<String> createArrayBlockingQueue(int capacity) {
        return new ArrayBlockingQueue<>(capacity);
    }

    /** Creates an optionally bounded LinkedBlockingQueue. */
    public BlockingQueue<String> createLinkedBlockingQueue(int capacity) {
        return new LinkedBlockingQueue<>(capacity);
    }

    /** Creates an unbounded LinkedBlockingQueue (capacity = Integer.MAX_VALUE). */
    public BlockingQueue<String> createUnboundedLinkedBlockingQueue() {
        return new LinkedBlockingQueue<>();
    }

    // ==================== Insert operations ====================

    /**
     * add(e) – Inserts element; throws IllegalStateException if the queue is full.
     * Use when you expect the queue to have space and want a fast failure.
     */
    public boolean add(BlockingQueue<String> queue, String element) {
        return queue.add(element); // throws IllegalStateException if full
    }

    /**
     * offer(e) – Inserts element; returns false if the queue is full (no exception, no blocking).
     * Use for non-blocking, best-effort insertion.
     */
    public boolean offer(BlockingQueue<String> queue, String element) {
        return queue.offer(element);
    }

    /**
     * put(e) – Inserts element; BLOCKS if the queue is full until space becomes available.
     * This is the classic producer operation — back-pressure is automatic.
     */
    public void put(BlockingQueue<String> queue, String element) throws InterruptedException {
        queue.put(element);
    }

    /**
     * offer(e, timeout, unit) – Inserts element; blocks up to the specified timeout.
     * Returns false if the timeout elapses before space is available.
     */
    public boolean offerWithTimeout(BlockingQueue<String> queue, String element,
                                    long timeout, TimeUnit unit) throws InterruptedException {
        return queue.offer(element, timeout, unit);
    }

    // ==================== Remove operations ====================

    /**
     * remove() – Retrieves and removes the head; throws NoSuchElementException if empty.
     */
    public String remove(BlockingQueue<String> queue) {
        return queue.remove(); // throws NoSuchElementException if empty
    }

    /**
     * poll() – Retrieves and removes the head; returns null if empty (no exception, no blocking).
     */
    public String poll(BlockingQueue<String> queue) {
        return queue.poll();
    }

    /**
     * take() – Retrieves and removes the head; BLOCKS if the queue is empty until an element is available.
     * This is the classic consumer operation.
     */
    public String take(BlockingQueue<String> queue) throws InterruptedException {
        return queue.take();
    }

    /**
     * poll(timeout, unit) – Retrieves and removes the head; blocks up to the specified timeout.
     * Returns null if the timeout elapses before an element is available.
     */
    public String pollWithTimeout(BlockingQueue<String> queue, long timeout, TimeUnit unit)
            throws InterruptedException {
        return queue.poll(timeout, unit);
    }

    // ==================== Examine operations ====================

    /**
     * element() – Retrieves but does NOT remove the head; throws NoSuchElementException if empty.
     */
    public String element(BlockingQueue<String> queue) {
        return queue.element();
    }

    /**
     * peek() – Retrieves but does NOT remove the head; returns null if empty.
     */
    public String peek(BlockingQueue<String> queue) {
        return queue.peek();
    }

    // ==================== Bulk / utility operations ====================

    /**
     * drainTo(collection) – Removes all available elements and adds them to the given collection.
     * More efficient than polling in a loop — performs a single lock acquisition.
     * Returns the number of elements drained.
     */
    public List<String> drainAll(BlockingQueue<String> queue) {
        List<String> drained = new ArrayList<>();
        queue.drainTo(drained);
        return drained;
    }

    /**
     * drainTo(collection, maxElements) – Drains at most maxElements.
     */
    public List<String> drainAtMost(BlockingQueue<String> queue, int max) {
        List<String> drained = new ArrayList<>();
        queue.drainTo(drained, max);
        return drained;
    }

    /**
     * remainingCapacity() – Returns the number of additional elements that the queue can accept
     * without blocking. For unbounded queues this returns Integer.MAX_VALUE.
     */
    public int remainingCapacity(BlockingQueue<String> queue) {
        return queue.remainingCapacity();
    }

    // ==================== Producer-Consumer demo ====================

    /**
     * Classic producer-consumer pattern.
     * Producer puts items into the queue; consumer takes them out.
     * Both block automatically when the queue is full/empty — no manual synchronization needed.
     *
     * @param itemCount number of items the producer will put
     * @param capacity  bounded queue capacity
     * @return items consumed by the consumer, in order
     */
    public List<String> producerConsumerDemo(int itemCount, int capacity) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(capacity);
        List<String> consumed = new ArrayList<>();
        String POISON_PILL = "DONE"; // sentinel to signal the consumer to stop

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= itemCount; i++) {
                    String item = "Item-" + i;
                    queue.put(item); // blocks if queue is full
                }
                queue.put(POISON_PILL); // signal end
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String item = queue.take(); // blocks if queue is empty
                    if (POISON_PILL.equals(item)) break;
                    consumed.add(item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        return consumed;
    }
}

