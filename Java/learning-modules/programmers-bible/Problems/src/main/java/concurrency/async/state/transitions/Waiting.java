package concurrency.async.state.transitions;

import concurrency.util.ThreadUtils;

public class Waiting {

    public static void main(String[] args) throws InterruptedException {
        /* **** Setup ******* */
        Runnable operationTask = () -> {
            System.out.println("Executing T1 thread");
            try {
                Thread.sleep(5000);
                ThreadUtils.printThreadState(Thread.currentThread(), "After 5 seconds, T1 thread started running");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Execution of T1 thread completed");

        };



        Runnable monitorTask = () -> {
            System.out.println("Executing T2 thread");
            Thread t1 = new Thread(operationTask);
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Execution of T2 thread completed");
            ThreadUtils.printThreadState(Thread.currentThread(), "After T1 completes, T2 thread started running");

        };
        Thread t2 = new Thread(monitorTask);

        /* *** Monitoring starts *** */
        t2.start();
        Thread.sleep(10); // Simulating some delay
        ThreadUtils.printThreadState(t2, "T2 awaiting T2's completion");

        ThreadUtils.printThreadState(Thread.currentThread(), "Main thread still running");
    }


}
