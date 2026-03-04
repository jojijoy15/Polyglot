package concurrency.async.state.transitions;

import concurrency.util.ThreadUtils;

public class Blocked {

    public static void main(String[] args) throws InterruptedException {
        /* **** Setup ******* */

        Object lock = new Object();

        Runnable taskOne = () -> {
            System.out.println("Executing T1 task");
            try {
                synchronized (lock) {
                    Thread.sleep(5000); //simulating work
                    lock.notifyAll(); // Important To notify other thread to acquire lock
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Execution of T1 task completed");

        };


        Runnable taskTwo =  () -> {
            System.out.println("Executing T2 task");
            Thread t1 = new Thread(taskOne);
            t1.start();
            try {
//                ThreadUtils.printThreadState(t1, "T1 state before T2 acquires lock..."); // Adds delay
                synchronized (lock) {
                    t1.join();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Execution of T2 thread completed");

        };
        Thread t2 = new Thread(taskTwo);

        /* *** Task starts *** */
        t2.start();
        Thread.sleep(10); // Simulating some delay
        ThreadUtils.printThreadState(t2, "T2 awaiting T1's completion");
        ThreadUtils.printThreadState(Thread.currentThread(), "Main thread still running");
        t2.join();
        ThreadUtils.printThreadState(t2, "T2 Completed");
    }


}
