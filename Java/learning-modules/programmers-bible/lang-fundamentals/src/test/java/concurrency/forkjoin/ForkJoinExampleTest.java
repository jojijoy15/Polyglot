package concurrency.forkjoin;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ForkJoinExampleTest {

    // ===================== RecursiveTask tests =====================

    @Test
    void sumTaskSmallArray() {
        int[] arr = {1, 2, 3, 4, 5};
        ForkJoinPool pool = new ForkJoinPool();

        long result = pool.invoke(new ForkJoinSumTask(arr, 0, arr.length));

        assertEquals(15L, result);
    }

    @Test
    void sumTaskLargeArray() {
        // Array [1..100], expected sum = 5050
        int[] arr = IntStream.rangeClosed(1, 100).toArray();
        ForkJoinPool pool = new ForkJoinPool();

        long result = pool.invoke(new ForkJoinSumTask(arr, 0, arr.length));

        assertEquals(5050L, result);
    }

    @Test
    void sumTaskEmptyArray() {
        int[] arr = {};
        ForkJoinPool pool = new ForkJoinPool();

        long result = pool.invoke(new ForkJoinSumTask(arr, 0, arr.length));

        assertEquals(0L, result);
    }

    @Test
    void sumTaskSingleElement() {
        int[] arr = {42};
        ForkJoinPool pool = new ForkJoinPool();

        long result = pool.invoke(new ForkJoinSumTask(arr, 0, arr.length));

        assertEquals(42L, result);
    }

    @Test
    void sumTaskWithCommonPool() {
        // You can also use the common pool (default pool shared by parallel streams)
        int[] arr = IntStream.rangeClosed(1, 50).toArray();

        long result = ForkJoinPool.commonPool()
                .invoke(new ForkJoinSumTask(arr, 0, arr.length));

        assertEquals(1275L, result);
    }

    // ===================== RecursiveAction tests =====================

    @Test
    void doubleActionSmallArray() {
        int[] arr = {1, 2, 3, 4, 5};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinDoubleAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{2, 4, 6, 8, 10}, arr);
    }

    @Test
    void doubleActionLargeArray() {
        int[] original = IntStream.rangeClosed(1, 100).toArray();
        int[] expected = IntStream.rangeClosed(1, 100).map(i -> i * 2).toArray();

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ForkJoinDoubleAction(original, 0, original.length));

        assertArrayEquals(expected, original);
    }

    @Test
    void doubleActionEmptyArray() {
        int[] arr = {};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinDoubleAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void doubleActionSingleElement() {
        int[] arr = {7};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinDoubleAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{14}, arr);
    }

    // ===================== MergeSort (RecursiveAction) tests =====================

    @Test
    void mergeSortAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinMergeSortAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void mergeSortReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinMergeSortAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void mergeSortUnsortedArray() {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinMergeSortAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{3, 9, 10, 27, 38, 43, 82}, arr);
    }

    @Test
    void mergeSortEmptyArray() {
        int[] arr = {};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinMergeSortAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void mergeSortSingleElement() {
        int[] arr = {42};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinMergeSortAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void mergeSortWithDuplicates() {
        int[] arr = {5, 3, 5, 1, 3, 2, 1};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinMergeSortAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 5, 5}, arr);
    }

    @Test
    void mergeSortWithNegativeNumbers() {
        int[] arr = {-3, 0, -1, 5, 2, -8};
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new ForkJoinMergeSortAction(arr, 0, arr.length));

        assertArrayEquals(new int[]{-8, -3, -1, 0, 2, 5}, arr);
    }

    @Test
    void mergeSortLargeArray_matchesArraysSort() {
        Random random = new Random(42);
        int[] arr = random.ints(1000, -500, 500).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ForkJoinMergeSortAction(arr, 0, arr.length));

        assertArrayEquals(expected, arr);
    }
}
