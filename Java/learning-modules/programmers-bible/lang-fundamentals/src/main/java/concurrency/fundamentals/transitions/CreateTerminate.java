package concurrency.fundamentals.transitions;

import concurrency.util.ThreadUtils;

public class CreateTerminate {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Executing task");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Thread t1 = new Thread(runnable);
        ThreadUtils.printThreadState(t1, "Before starting thread");
        t1.start();
        ThreadUtils.printThreadState(t1, "After starting thread");
        ThreadUtils.printThreadState(Thread.currentThread(), "Main thread still running");
        Thread.sleep(2000);
        ThreadUtils.printThreadState(t1, "Thread t1 state");
        t1.join();      //join -> blocking call
        ThreadUtils.printThreadState(t1, "Created thread is done");
    }


}
