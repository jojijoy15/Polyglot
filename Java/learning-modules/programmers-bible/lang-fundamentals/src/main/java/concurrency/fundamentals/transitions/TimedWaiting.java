package concurrency.fundamentals.transitions;

import concurrency.util.ThreadUtils;

public class TimedWaiting {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Executing T1 thread");
            try {
                Thread.sleep(5000);
                ThreadUtils.printThreadState(Thread.currentThread(), "After 5 seconds, T1 thread started running");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Execution of T1 thread completed");

        };
        Thread t1 = new Thread(runnable);
        ThreadUtils.printThreadState(t1, "Before T1 starting thread");
        t1.start();
        Thread.sleep(10); //Simulate delay
        ThreadUtils.printThreadState(t1, "After T1 starting thread");
        ThreadUtils.printThreadState(Thread.currentThread(), "Main thread still running");
    }


}
