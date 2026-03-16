package com.problems.learning.algo.simple;

public class OddEvenPrinter {

    private Integer counter = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        OddEvenPrinter oddEvenPrinter = new OddEvenPrinter();
        Thread evenThread = new Thread(oddEvenPrinter.evenTask());
        Thread oddThread = new Thread(oddEvenPrinter.oddTask());
        evenThread.start();
        oddThread.start();
    }

    public Runnable evenTask() {
        return () -> {
            while (true) {
                synchronized (lock) {
                    if ((counter & 1) == 0) {
                        System.out.println("Even Thread: " + (counter++));
                    } try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    public Runnable oddTask() {
        return () -> {
            while (true) {
                synchronized (lock) {
                    if ((counter & 1) == 1) {
                        System.out.println("Odd Thread: " + (counter++));
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
