package concurrency.issues;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrencyIssuesTest {

    // ===================== Deadlock =====================

    @Test
    void deadlock_threadsGetStuck() throws InterruptedException {
        DeadlockDemo demo = new DeadlockDemo();
        Thread[] threads = demo.startDeadlock();

        // Give the threads time to reach deadlock
        Thread.sleep(1000);

        // Both threads should be BLOCKED — waiting for a lock the other holds
        Thread.State t1State = getState(threads);
        Thread.State t2State = threads[1].getState();

        assertTrue(
                t1State == Thread.State.BLOCKED && t2State == Thread.State.BLOCKED,
                "Both threads should be BLOCKED in a deadlock. " +
                        "t1=" + t1State + ", t2=" + t2State
        );

        // Clean up: interrupt the deadlocked threads (they won't respond to interrupt
        // while waiting for a synchronized block, but we mark them anyway)
        threads[0].interrupt();
        threads[1].interrupt();
        // Note: In real code, deadlocked threads can only be stopped by killing the JVM
        // or by using tryLock/Lock.lockInterruptibly instead of synchronized.
    }

    private static Thread.State getState(Thread[] threads) {
        Thread.State t1State = threads[0].getState();
        return t1State;
    }

    @Test
    void fixedLockOrder_completesWithoutDeadlock() throws InterruptedException {
        DeadlockDemo demo = new DeadlockDemo();

        // This should complete quickly — no deadlock
        assertTimeoutPreemptively(
                java.time.Duration.ofSeconds(5),
                () -> demo.startWithConsistentLockOrder()
        );
    }

    // ===================== Starvation =====================

    @Test
    void unfairLock_someThreadsGetStarved() throws InterruptedException {
        // With an unfair lock and 4 threads, the distribution is typically uneven.
        StarvationDemo demo = new StarvationDemo(10_000);
        int[] counts = demo.runWithUnfairLock(4);

        System.out.println("Unfair lock counts: " + Arrays.toString(counts));

        // All threads should have completed all their iterations
        int total = Arrays.stream(counts).sum();
        assertEquals(40_000, total, "Total executions should equal threadCount * iterations");

        // With an unfair lock each thread still completes its own loop of 10_000,
        // but the relative ordering/timing can vary significantly.
    }

    @Test
    void fairLock_allThreadsGetEqualAccess() throws InterruptedException {
        StarvationDemo demo = new StarvationDemo(10_000);
        int[] counts = demo.runWithFairLock(4);

        System.out.println("Fair lock counts: " + Arrays.toString(counts));

        // Every thread should have executed exactly iterations times
        for (int count : counts) {
            assertEquals(10_000, count, "Each thread should complete all iterations");
        }
    }

    // ===================== Livelock =====================

    @Test
    void livelock_workersKeepYielding() throws InterruptedException {
        // With 1000 max attempts, the workers will yield many times
        LivelockDemo demo = new LivelockDemo(1000);
        int yieldCount = demo.runLivelock();

        System.out.println("Livelock total yields: " + yieldCount);

        // Expect a large number of yields — both workers kept stepping aside
        assertTrue(yieldCount > 10,
                "Workers should have yielded multiple times in a livelock. Yields: " + yieldCount);
    }

    @Test
    void randomBackoff_bothWorkersComplete() throws InterruptedException {
        LivelockDemo demo = new LivelockDemo(1000);
        boolean bothDone = demo.runWithRandomBackoff();

        assertTrue(bothDone, "With random back-off, both workers should complete");
    }
}

