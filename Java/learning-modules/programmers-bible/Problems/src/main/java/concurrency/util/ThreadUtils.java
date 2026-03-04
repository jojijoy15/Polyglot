package concurrency.util;

public class ThreadUtils {

    public static void printThreadState(Thread thread, String description) {
        String printStatement = "Thread Name : " + thread.getName() + ", with state : " + thread.getState();
        if (null != description && !description.isBlank()) {
            printStatement += ", Description : " + description;
        }
        System.out.println(printStatement);
    }
}
