package com.problems.learning.concurrency.oddeven;

import com.problems.learning.tags.Medium;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * Print Zero Even Odd — ReentrantLock + Condition approach.
 *
 * Three threads call zero(), even(), odd() concurrently.
 * Output: "010203040506..." (length 2n).
 *
 * State machine:
 *   printZero=true  → zero thread prints 0, sets printZero=false
 *   printZero=false → odd/even thread prints current, increments, sets printZero=true
 */
@Medium
class ZeroEvenOddLock {
    private final int n;
    private int current = 1;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition zeroCondition = lock.newCondition();
    private final Condition oddCondition = lock.newCondition();
    private final Condition evenCondition = lock.newCondition();
    private boolean printZero;

    public ZeroEvenOddLock(int n) {
        this.n = n;
        this.printZero = true; // zero goes first
    }

    // Thread A: only prints 0's
    public void zero(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                while (current <= n && !printZero) {
                    zeroCondition.await();
                }
                if (current > n) break;

                printNumber.accept(0);
                printZero = false;

                // Wake the right thread based on whether current is odd or even
                if (current % 2 == 1) {
                    oddCondition.signalAll();
                } else {
                    evenCondition.signalAll();
                }
            }
            // Ensure waiting threads can exit when done
            oddCondition.signalAll();
            evenCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Thread B: only prints even numbers
    public void even(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                while (current <= n && (current % 2 != 0 || printZero)) {
                    evenCondition.await();
                }
                if (current > n) break;

                printNumber.accept(current);
                current++;
                printZero = true;
                zeroCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    // Thread C: only prints odd numbers
    public void odd(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                while (current <= n && (current % 2 == 0 || printZero)) {
                    oddCondition.await();
                }
                if (current > n) break;

                printNumber.accept(current);
                current++;
                printZero = true;
                zeroCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    // --- Demo ---
    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddLock zeo = new ZeroEvenOddLock(5);
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

