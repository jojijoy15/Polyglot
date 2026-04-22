package concurrency.issues;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demonstrates <b>Livelock</b>.
 * <p>
 * Livelock is similar to deadlock — threads can't make progress — but instead of
 * being BLOCKED, they are actively running, continuously <b>reacting to each other</b>
 * and undoing their work, without ever moving forward.
 * <p>
 * Real-world analogy: Two people meet in a narrow hallway. Both step aside to let
 * the other pass — but they both step to the same side, then back, then same side
 * again … neither ever passes.
 * <p>
 * <b>How to prevent:</b>
 * <ul>
 *   <li>Introduce <b>randomized back-off</b> — wait a random duration before retrying.</li>
 *   <li>Add a <b>retry limit</b> and escalate.</li>
 *   <li>Use {@code tryLock()} with a timeout.</li>
 * </ul>
 */
public class LivelockDemo {

    private final int maxAttempts;

    public LivelockDemo(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    /**
     * Simulates two polite workers who keep yielding a shared resource to each other.
     * <p>
     * Worker-1 acquires the resource, sees Worker-2 also wants it, releases it.
     * Worker-2 acquires the resource, sees Worker-1 also wants it, releases it.
     * Both keep "politely" stepping aside — neither finishes.
     *
     * @return total yield count from both workers (high = livelock detected)
     */
    public int runLivelock() throws InterruptedException {
        AtomicBoolean resourceFree = new AtomicBoolean(true);
        int[] yieldCount = {0};

        AtomicBoolean worker1Wants = new AtomicBoolean(true);
        AtomicBoolean worker2Wants = new AtomicBoolean(true);
        AtomicBoolean worker1Done = new AtomicBoolean(false);
        AtomicBoolean worker2Done = new AtomicBoolean(false);

        Thread worker1 = new Thread(() -> {
            int attempts = 0;
            while (worker1Wants.get() && attempts < maxAttempts) {
                if (resourceFree.compareAndSet(true, false)) {
                    if (worker2Wants.get() && !worker2Done.get()) {
                        // "Politely" yield the resource
                        resourceFree.set(true);
                        yieldCount[0]++;
                        attempts++;
                        Thread.yield();
                    } else {
                        // Other worker doesn't want it — do the work
                        worker1Wants.set(false);
                        worker1Done.set(true);
                        resourceFree.set(true);
                    }
                }
            }
        }, "livelock-worker-1");

        Thread worker2 = new Thread(() -> {
            int attempts = 0;
            while (worker2Wants.get() && attempts < maxAttempts) {
                if (resourceFree.compareAndSet(true, false)) {
                    if (worker1Wants.get() && !worker1Done.get()) {
                        resourceFree.set(true);
                        yieldCount[0]++;
                        attempts++;
                        Thread.yield();
                    } else {
                        worker2Wants.set(false);
                        worker2Done.set(true);
                        resourceFree.set(true);
                    }
                }
            }
        }, "livelock-worker-2");

        worker1.start();
        worker2.start();
        worker1.join();
        worker2.join();

        return yieldCount[0];
    }

    /**
     * Fixed version using <b>tryLock with random back-off</b>.
     * <p>
     * Each worker tries to acquire two locks. If it can't get the second lock,
     * it releases the first and waits a <b>random</b> time before retrying.
     * The randomness breaks the symmetry and lets one worker proceed.
     *
     * @return true if both workers completed successfully
     */
    public boolean runWithRandomBackoff() throws InterruptedException {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();
        AtomicBoolean done1 = new AtomicBoolean(false);
        AtomicBoolean done2 = new AtomicBoolean(false);

        Thread worker1 = new Thread(() -> {
            while (!done1.get()) {
                lock1.lock();
                try {
                    if (lock2.tryLock()) {
                        try { done1.set(true); } finally { lock2.unlock(); }
                    }
                } finally {
                    lock1.unlock();
                }
                if (!done1.get()) {
                    try { Thread.sleep((long) (Math.random() * 50) + 1); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                }
            }
        }, "backoff-worker-1");

        Thread worker2 = new Thread(() -> {
            while (!done2.get()) {
                lock2.lock();
                try {
                    if (lock1.tryLock()) {
                        try { done2.set(true); } finally { lock1.unlock(); }
                    }
                } finally {
                    lock2.unlock();
                }
                if (!done2.get()) {
                    try { Thread.sleep((long) (Math.random() * 50) + 1); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                }
            }
        }, "backoff-worker-2");

        worker1.start();
        worker2.start();
        worker1.join();
        worker2.join();

        return done1.get() && done2.get();
    }
}

