package concurrency.issues;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Demonstrates <b>Starvation</b>.
 * <p>
 * Starvation occurs when a thread is perpetually denied access to a shared resource
 * because other threads are continuously given priority.
 * <p>
 * Common causes:
 * <ul>
 *   <li>Using an <b>unfair lock</b> (default {@link ReentrantLock}) under heavy contention —
 *       a thread that just released the lock can immediately re-acquire it before a waiting
 *       thread gets a chance (barging).</li>
 *   <li>Thread priorities: a low-priority thread may rarely get CPU time if higher-priority
 *       threads are always runnable.</li>
 *   <li>Holding locks for very long durations.</li>
 * </ul>
 * <p>
 * <b>How to prevent:</b>
 * <ul>
 *   <li>Use a <b>fair lock</b>: {@code new ReentrantLock(true)} — FIFO ordering of lock requests.</li>
 *   <li>Avoid excessively long critical sections.</li>
 *   <li>Don't rely on thread priorities for correctness.</li>
 * </ul>
 */
public class StarvationDemo {

    private final int iterations;

    public StarvationDemo(int iterations) {
        this.iterations = iterations;
    }

    /**
     * Demonstrates starvation using an <b>unfair lock</b> (default).
     * <p>
     * The greedy thread re-acquires the lock in a tight loop, starving other threads.
     *
     * @param threadCount  number of worker threads (one will be greedy)
     * @return per-thread execution counts — starved threads will have very low counts
     */
    public int[] runWithUnfairLock(int threadCount) throws InterruptedException {
        ReentrantLock unfairLock = new ReentrantLock(false); // unfair (default)
        return run(unfairLock, threadCount);
    }

    /**
     * Fixed version using a <b>fair lock</b>.
     * <p>
     * The lock is handed out in FIFO order, so no thread gets starved.
     *
     * @param threadCount  number of worker threads
     * @return per-thread execution counts — all threads will have roughly equal counts
     */
    public int[] runWithFairLock(int threadCount) throws InterruptedException {
        ReentrantLock fairLock = new ReentrantLock(true); // fair — FIFO ordering
        return run(fairLock, threadCount);
    }

    private int[] run(ReentrantLock lock, int threadCount) throws InterruptedException {
        int[] counts = new int[threadCount];
        Thread[] threads = new Thread[threadCount];

        for (int t = 0; t < threadCount; t++) {
            final int index = t;
            threads[t] = new Thread(() -> {
                for (int i = 0; i < iterations; i++) {
                    lock.lock();
                    try {
                        long timeStart = System.currentTimeMillis();
                        counts[index]++;
                        // Simulate some work inside the critical section
                        long timeEnd = System.currentTimeMillis();
                        System.out.println(Thread.currentThread().getName() + ": timing" + (timeEnd - timeStart));

                    } finally {
                        lock.unlock();
                    }
                }
            }, "worker-" + t);
        }

        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();

        return counts;
    }
}

