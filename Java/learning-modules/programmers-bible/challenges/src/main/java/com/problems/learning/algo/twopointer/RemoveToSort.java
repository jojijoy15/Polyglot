package com.problems.learning.algo.twopointer;

public class RemoveToSort {

    public int findLengthOfShortestSubarray(int[] arr) {
        int n = arr.length;

        // Step 1: find prefix
        int left = 0;
        while (left < n - 1 && arr[left] <= arr[left + 1]) {
            left++;
        }

        // Already sorted
        if (left == n - 1) {
            // int[] result = {0, -1, -1};
            return 0;
        }

        // Step 2: find suffix
        int right = n - 1;
        while (right > 0 && arr[right - 1] <= arr[right]) {
            right--;
        }
        /*
        int minLen = n;
        int start = 0, end = n - 1;

        if (right < minLen) {
            minLen = right;
            start = 0;
            end = right - 1;
        }

        if (n - left - 1 < minLen) {
            minLen = n - left - 1;
            start = left + 1;
            end = n - 1;
        }
        */

        int result = Math.min(n - left - 1, right);

        // Step 3: merge
        int i = 0, j = right;
        while (i <= left && j < n) {
            if (arr[i] <= arr[j]) {
                result = Math.min(result, j - i - 1);
                /*
                if (j - i - 1 < minLen) {
                    minLen = j - i - 1;
                    start = i + 1;
                    end = j - 1;
                }
                 */
                i++;
            } else {
                j++;
            }
        }

        return result; // return new int[]{minLen, start, end);
    }
}
