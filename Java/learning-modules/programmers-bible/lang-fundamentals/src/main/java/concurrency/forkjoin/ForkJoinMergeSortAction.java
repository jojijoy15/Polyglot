package concurrency.forkjoin;

import java.util.concurrent.RecursiveAction;

/**
 * Parallel Merge Sort using the Fork/Join framework (RecursiveAction).
 * <p>
 * How it works:
 * <ol>
 *   <li>If the sub-array size ≤ THRESHOLD, sort it with insertion sort (avoids fork overhead for tiny arrays).</li>
 *   <li>Otherwise, split the array at the midpoint.</li>
 *   <li>Fork the left half (submit to the pool), compute the right half in the current thread.</li>
 *   <li>Join the left half, then merge the two sorted halves back into the original array.</li>
 * </ol>
 * Uses a temporary auxiliary array for the merge step.
 */
public class ForkJoinMergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 8;

    private final int[] array;
    private final int start; // inclusive
    private final int end;   // exclusive

    public ForkJoinMergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int length = end - start;

        // Base case – small enough to sort directly with insertion sort
        if (length <= THRESHOLD) {
            insertionSort(array, start, end);
            return;
        }

        int mid = start + length / 2;

        ForkJoinMergeSortAction leftTask = new ForkJoinMergeSortAction(array, start, mid);
        ForkJoinMergeSortAction rightTask = new ForkJoinMergeSortAction(array, mid, end);

        // Fork left (runs async in the pool), compute right in current thread
        leftTask.fork();
        rightTask.compute();
        leftTask.join();

        // Both halves are sorted – merge them
        merge(array, start, mid, end);
    }

    /**
     * Merges two sorted sub-arrays [start, mid) and [mid, end) into a single sorted sub-array.
     */
    private void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[end - start];
        int i = start;   // pointer for left half
        int j = mid;     // pointer for right half
        int k = 0;       // pointer for temp array

        while (i < mid && j < end) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // Copy remaining elements from left half
        while (i < mid) {
            temp[k++] = arr[i++];
        }

        // Copy remaining elements from right half
        while (j < end) {
            temp[k++] = arr[j++];
        }

        // Copy merged result back into original array
        System.arraycopy(temp, 0, arr, start, temp.length);
    }

    /**
     * Simple insertion sort for small sub-arrays – avoids fork/join overhead.
     */
    private void insertionSort(int[] arr, int start, int end) {
        for (int i = start + 1; i < end; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= start && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}

