package com.problems.learning.concurrency.oddeven;

import com.problems.learning.tags.Medium;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * Print Zero Even Odd — Semaphore approach.
 *
 * Three threads call zero(), even(), odd() concurrently.
 * Output: "010203040506..." (length 2n).
 *
 * Three semaphores control the turn order:
 *   zeroSem(1) → zero goes first
 *   oddSem(0)  → odd waits until zero releases it
 *   evenSem(0) → even waits until zero releases it
 *
 * Flow for n=5:
 *   zero acquires zeroSem → prints 0 → releases oddSem  (current=1 is odd)
 *   odd  acquires oddSem  → prints 1 → releases zeroSem
 *   zero acquires zeroSem → prints 0 → releases evenSem (current=2 is even)
 *   even acquires evenSem → prints 2 → releases zeroSem
 *   ... and so on
 */
@Medium
class ZeroEvenOddSemaphore {
    private final int n;

    private final Semaphore zeroSem = new Semaphore(1); // zero starts first
    private final Semaphore oddSem = new Semaphore(0);
    private final Semaphore evenSem = new Semaphore(0);

    public ZeroEvenOddSemaphore(int n) {
        this.n = n;
    }

    // Thread A: only prints 0's
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroSem.acquire();       // wait for our turn
            printNumber.accept(0);
            if (i % 2 == 1) {
                oddSem.release();    // next number is odd → wake odd thread
            } else {
                evenSem.release();   // next number is even → wake even thread
            }
        }
    }

    // Thread B: only prints even numbers (2, 4, 6, ...)
    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            evenSem.acquire();       // wait for zero to hand us the turn
            printNumber.accept(i);
            zeroSem.release();       // hand turn back to zero
        }
    }

    // Thread C: only prints odd numbers (1, 3, 5, ...)
    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            oddSem.acquire();        // wait for zero to hand us the turn
            printNumber.accept(i);
            zeroSem.release();       // hand turn back to zero
        }
    }

    // --- Demo ---
    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddSemaphore zeo = new ZeroEvenOddSemaphore(5);
        StringBuilder sb = new StringBuilder();
        IntConsumer printer = sb::append;

        Thread t1 = new Thread(() -> { try { zeo.zero(printer); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        Thread t2 = new Thread(() -> { try { zeo.even(printer); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        Thread t3 = new Thread(() -> { try { zeo.odd(printer); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });

        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();

        System.out.println(sb); // 0102030405
    }
}


