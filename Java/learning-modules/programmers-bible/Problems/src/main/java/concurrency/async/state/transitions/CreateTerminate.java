package concurrency.async.state.transitions;

import concurrency.util.ThreadUtils;

public class CreateTerminate {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Executing task");
        };
        Thread t1 = new Thread(runnable);
        ThreadUtils.printThreadState(t1, "Before starting thread");
        t1.start();
        ThreadUtils.printThreadState(t1, "After starting thread");
        ThreadUtils.printThreadState(Thread.currentThread(), "Main thread still running");
        t1.join();      //join -> blocking call
        ThreadUtils.printThreadState(t1, "Created thread is done");
    }


}
