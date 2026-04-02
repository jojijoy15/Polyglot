package com.problems.learning.algo.simple.binarysearch;

public class LastOccurrence {

    // Given a sorted array of N-1 distinct numbers in the range [1, N],
    // find the one missing number using Binary Search. O(log N)
    public int foundLastAt(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                while(mid < arr.length && arr[mid] == target) {
                    mid = mid + 1;
                }
                return mid - 1;
            } else if (arr[mid] < target) {
                // arr[mid] > mid + 1 → a number is missing on the left side (or at mid)
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // 'left' now points to the index where the mismatch begins.
        // The missing number is left + 1.
        return -1;
    }
}
