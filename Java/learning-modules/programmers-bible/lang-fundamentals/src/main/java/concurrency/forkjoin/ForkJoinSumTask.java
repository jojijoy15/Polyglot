package concurrency.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask example: computes the sum of an int array in parallel.
 * When the sub-array is small enough (≤ THRESHOLD), it sums sequentially;
 * otherwise it forks two halves and joins the results.
 */
public class ForkJoinSumTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 5;

    private final int[] array;
    private final int start;
    private final int end;

    public ForkJoinSumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;

        // Base case – small enough to compute directly
        if (length <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }

        // Split into two halves
        int mid = start + length / 2;

        ForkJoinSumTask leftTask = new ForkJoinSumTask(array, start, mid);
        ForkJoinSumTask rightTask = new ForkJoinSumTask(array, mid, end);

        // Fork the left task (runs asynchronously)
        leftTask.fork();

        // Compute the right task in the current thread
        long rightResult = rightTask.compute();

        // Join the left task's result (waits if necessary)
        long leftResult = leftTask.join();

        return leftResult + rightResult;
    }
}
