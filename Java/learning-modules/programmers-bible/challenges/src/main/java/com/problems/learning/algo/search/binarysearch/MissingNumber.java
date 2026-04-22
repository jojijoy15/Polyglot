package com.problems.learning.algo.search.binarysearch;

public class MissingNumber {

    // Given a sorted array of N-1 distinct numbers in the range [1, N],
    // find the one missing number using Binary Search. O(log N)
    public int findMissing(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == mid + 1) {
                left = mid + 1;
            } else {
                // arr[mid] > mid + 1 → a number is missing on the left side (or at mid)
                right = mid - 1;
            }
        }
        // 'left' now points to the index where the mismatch begins.
        // The missing number is left + 1.
        return left + 1;
    }
}
