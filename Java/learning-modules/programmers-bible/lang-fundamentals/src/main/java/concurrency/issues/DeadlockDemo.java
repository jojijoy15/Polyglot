package concurrency.issues;

/**
 * Demonstrates a classic <b>Deadlock</b> scenario.
 * <p>
 * Deadlock occurs when two (or more) threads each hold a lock that the other needs,
 * and neither can proceed because they are waiting for the other to release.
 * <p>
 * The classic "lock-ordering" deadlock:
 * <pre>
 *   Thread-1: lock(A) → tries lock(B) → BLOCKED (Thread-2 holds B)
 *   Thread-2: lock(B) → tries lock(A) → BLOCKED (Thread-1 holds A)
 * </pre>
 * Neither thread can make progress — they are permanently stuck.
 * <p>
 * <b>How to prevent:</b>
 * <ul>
 *   <li>Always acquire locks in a <b>consistent global order</b> (e.g., always A before B).</li>
 *   <li>Use {@code tryLock()} with a timeout to detect and back off.</li>
 *   <li>Use higher-level concurrency utilities (e.g., {@code java.util.concurrent} classes).</li>
 * </ul>
 */
public class DeadlockDemo {

    private final Object lockA = new Object();
    private final Object lockB = new Object();

    private volatile boolean thread1AcquiredFirstLock = false;
    private volatile boolean thread2AcquiredFirstLock = false;

    /**
     * Starts two threads that will deadlock.
     * <p>
     * Thread-1 locks A then tries B.
     * Thread-2 locks B then tries A.
     *
     * @return the two deadlocked threads so the caller can inspect their states
     */
    public Thread[] startDeadlock() {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                thread1AcquiredFirstLock = true;
                // Wait until thread-2 has also acquired its first lock
                while (!thread2AcquiredFirstLock) Thread.yield();

                // Now try to acquire lockB — but thread-2 holds it → BLOCKED
                synchronized (lockB) {
                    System.out.println("Thread-1: acquired both locks (this will never print in deadlock)");
                }
            }
        }, "deadlock-thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                thread2AcquiredFirstLock = true;
                // Wait until thread-1 has also acquired its first lock
                while (!thread1AcquiredFirstLock) Thread.yield();

                // Now try to acquire lockA — but thread-1 holds it → BLOCKED
                synchronized (lockA) {
                    System.out.println("Thread-2: acquired both locks (this will never print in deadlock)");
                }
            }
        }, "deadlock-thread-2");

        t1.start();
        t2.start();
        return new Thread[]{t1, t2};
    }

    /**
     * Fixed version — both threads acquire locks in the <b>same order</b> (A → B).
     * No deadlock is possible.
     */
    public void startWithConsistentLockOrder() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                synchronized (lockB) {
                    System.out.println("Thread-1: acquired A then B");
                }
            }
        }, "fixed-thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lockA) { // same order: A first, then B
                synchronized (lockB) {
                    System.out.println("Thread-2: acquired A then B");
                }
            }
        }, "fixed-thread-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

