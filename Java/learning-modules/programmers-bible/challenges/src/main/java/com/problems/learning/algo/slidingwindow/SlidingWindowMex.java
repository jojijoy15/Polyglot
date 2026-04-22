package com.problems.learning.algo.slidingwindow;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Sliding Window MEX (Minimum EXcludant)
 * <p>
 * For each window of size k, find the smallest non-negative integer
 * NOT present in the window.
 * <p>
 * Key insight: MEX of a window of size k is at most k (pigeonhole principle),
 * so we only need to track counts for values in [0, k].
 * <p>
 * Approach:
 * - Maintain a frequency count array of size (k+1) for the current window.
 * - Maintain a TreeSet of "absent" values (values with count == 0) in [0, k].
 * - MEX = smallest element in the absent set → TreeSet.first() in O(log k).
 * - Slide: remove outgoing element, add incoming element, update counts and absent set.
 * <p>
 * Time:  O(n log k)
 * Space: O(k)
 */
public class SlidingWindowMex {

    public static void main(String[] args) {
        int[] arr = {1, 2, 1, 0, 5, 1, 1, 0};
        int k = 3;

        int[] result = mexSlidingWindow(arr, k);
        // 0 3 2 2 0 2
        System.out.println(Arrays.toString(result));
    }

    public static int[] mexSlidingWindow(int[] arr, int k) {
        int n = arr.length;
        int[] result = new int[n - k + 1];

        int[] count = new int[k + 1];

        TreeSet<Integer> absent = new TreeSet<>();
        for (int v = 0; v <= k; v++) {
            absent.add(v); // initially all absent
        }

        for (int i = 0; i < k; i++) {
            addValue(arr[i], count, absent, k);
        }
        result[0] = absent.first(); // MEX = smallest absent value

        for (int i = 1; i <= n - k; i++) {
            removeValue(arr[i - 1], count, absent, k);   // element leaving the window
            addValue(arr[i + k - 1], count, absent, k);   // element entering the window
            result[i] = absent.first();
        }

        return result;
    }

    private static void addValue(int val, int[] count, TreeSet<Integer> absent, int k) {
        if (val <= k) {
            count[val]++;
            if (count[val] == 1) { // was absent, now present
                absent.remove(val);
            }
        }
    }

    private static void removeValue(int val, int[] count, TreeSet<Integer> absent, int k) {
        if (val <= k) {
            count[val]--;
            if (count[val] == 0) { // now absent
                absent.add(val);
            }
        }
    }
}