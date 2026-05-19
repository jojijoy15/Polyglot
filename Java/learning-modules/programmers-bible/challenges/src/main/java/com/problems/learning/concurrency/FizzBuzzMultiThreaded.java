package com.problems.learning.concurrency;

import com.problems.learning.tags.Medium;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

@Medium
public class FizzBuzzMultiThreaded {

    private final int n;
    private int current = 1;

    private final ReentrantLock lock = new ReentrantLock();

    // 3 Conditions: one for fizz/buzz/fizzbuzz threads, one shared isn't enough —
    // we use 3 conditions to signal specific thread groups:
    //   divisibleBy3    → fizz or fizzbuzz thread
    //   divisibleBy5    → buzz or fizzbuzz thread
    //   notDivisible    → number thread
    private final Condition divisibleBy3 = lock.newCondition();
    private final Condition divisibleBy5 = lock.newCondition();
    private final Condition notDivisible = lock.newCondition();

    public FizzBuzzMultiThreaded(int n) {
        this.n = n;
    }

    // Thread A: prints "fizz" for numbers divisible by 3 but NOT 5
    public void fizz(Runnable printFizz) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                while (current <= n && !(current % 3 == 0 && current % 5 != 0)) {
                    divisibleBy3.await();
                }
                if (current > n) break;

                printFizz.run();
                current++;
                signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    // Thread B: prints "buzz" for numbers divisible by 5 but NOT 3
    public void buzz(Runnable printBuzz) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                while (current <= n && !(current % 5 == 0 && current % 3 != 0)) {
                    divisibleBy5.await();
                }
                if (current > n) break;

                printBuzz.run();
                current++;
                signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    // Thread C: prints "fizzbuzz" for numbers divisible by both 3 and 5
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                while (current <= n && !(current % 3 == 0 && current % 5 == 0)) {
                    // Wait on both conditions — fizzbuzz needs divisible by 3 AND 5
                    // We pick divisibleBy3 here; signalAll wakes all conditions anyway
                    divisibleBy3.await();
                }
                if (current > n) break;

                printFizzBuzz.run();
                current++;
                signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    // Thread D: prints the number if not divisible by 3 or 5
    public void number(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                while (current <= n && (current % 3 == 0 || current % 5 == 0)) {
                    notDivisible.await();
                }
                if (current > n) break;

                printNumber.accept(current);
                current++;
                signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Signal all 3 conditions so every waiting thread re-checks its predicate.
     * Only the thread whose condition matches will proceed; others go back to waiting.
     */
    private void signalAll() {
        divisibleBy3.signalAll();
        divisibleBy5.signalAll();
        notDivisible.signalAll();
    }

    // --- Demo ---
    public static void main(String[] args) {
        int n = 15;
        FizzBuzzMultiThreaded fb = new FizzBuzzMultiThreaded(n);

        Thread threadA = new Thread(() -> {
            try { fb.fizz(() -> System.out.println("fizz")); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread threadB = new Thread(() -> {
            try { fb.buzz(() -> System.out.println("buzz")); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread threadC = new Thread(() -> {
            try { fb.fizzbuzz(() -> System.out.println("fizzbuzz")); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread threadD = new Thread(() -> {
            try { fb.number(num -> System.out.println(num)); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }
}

