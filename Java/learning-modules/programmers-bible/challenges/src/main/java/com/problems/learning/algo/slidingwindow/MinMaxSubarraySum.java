package com.problems.learning.algo.slidingwindow;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;

public class MinMaxSubarraySum {
    /**
     * Find the sum of (min + max) of every subarray of size k.
     *
     * Uses two monotonic deques:
     *   - maxDeque (decreasing): front always holds the window max index
     *   - minDeque (increasing): front always holds the window min index
     *
     * Each element enters and exits each deque at most once -> O(n) total.
     *
     * Time: O(n), Space: O(k)
     */
    public int sumOfMinMax(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) return 0;
        Deque<Integer> maxDeque = new ArrayDeque<>(); // monotonic decreasing (indices)
        Deque<Integer> minDeque = new ArrayDeque<>(); // monotonic increasing (indices)
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            // Remove elements that fell outside the window from front
            if (!maxDeque.isEmpty() && maxDeque.peekFirst() <= i - k) {
                maxDeque.pollFirst();
            }
            if (!minDeque.isEmpty() && minDeque.peekFirst() <= i - k) {
                minDeque.pollFirst();
            }
            // Maintain monotonic decreasing for max deque
            // Remove smaller elements from back - they can never be the max
            while (!maxDeque.isEmpty() && arr[maxDeque.peekLast()] <= arr[i]) {
                maxDeque.pollLast();
            }
            // Maintain monotonic increasing for min deque
            // Remove larger elements from back - they can never be the min
            while (!minDeque.isEmpty() && arr[minDeque.peekLast()] >= arr[i]) {
                minDeque.pollLast();
            }
            maxDeque.offerLast(i);
            minDeque.offerLast(i);
            // Window is fully formed when i >= k - 1
            if (i >= k - 1) {
                sum += arr[maxDeque.peekFirst()] + arr[minDeque.peekFirst()];
            }
        }
        return sum;
    }

    // TODO verify time complexity
    // O(n log k) time, O(k) space — single TreeMap, no deques
    // TreeMap keeps elements sorted: firstKey() = min, lastKey() = max
    public int sumOfMinMaxUsingTreeMap(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0 || k > arr.length) return 0;

        TreeMap<Integer, Integer> window = new TreeMap<>(); // value → frequency
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            // Add current element
            window.merge(arr[i], 1, Integer::sum);

            // Remove element that fell outside the window
            if (i >= k) {
                int removed = arr[i - k];
                if (window.get(removed) == 1) {
                    window.remove(removed);
                } else {
                    window.merge(removed, -1, Integer::sum);
                }
            }

            // Window fully formed
            if (i >= k - 1) {
                sum += window.firstKey() + window.lastKey();
            }
        }

        return sum;
    }
}
