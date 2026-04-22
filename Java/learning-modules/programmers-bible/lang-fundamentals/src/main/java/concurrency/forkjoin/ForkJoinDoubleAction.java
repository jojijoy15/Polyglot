package concurrency.forkjoin;

import java.util.concurrent.RecursiveAction;

/**
 * RecursiveAction example: doubles every element of an int array in-place.
 * RecursiveAction returns no result (void) – useful for side-effect work.
 */
public class ForkJoinDoubleAction extends RecursiveAction {

    private static final int THRESHOLD = 5;

    private final int[] array;
    private final int start;
    private final int end;

    public ForkJoinDoubleAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int length = end - start;

        // Base case – small enough to compute directly
        if (length <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                array[i] *= 2;
            }
            return;
        }

        // Split into two halves
        int mid = start + length / 2;

        ForkJoinDoubleAction leftAction = new ForkJoinDoubleAction(array, start, mid);
        ForkJoinDoubleAction rightAction = new ForkJoinDoubleAction(array, mid, end);

        // invokeAll forks both and waits for both to complete
        invokeAll(leftAction, rightAction);
    }
}
