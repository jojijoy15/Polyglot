package com.problems.learning.algo.simple.binarysearch;

public class FindTargetRotated {

    // Given a rotated sorted array of N-1 distinct numbers in the range [1, N],
    // find the target using Binary Search. O(log N)
    public int findTarget(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            if (arr[left] <= arr[mid]) {
                if (target >= arr[left] && target < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Right half is sorted
            else {
                if (target > arr[mid] && target <= arr[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        // 'left' now points to the index where the mismatch begins.
        // The missing number is left + 1.
        return -1;
    }

    // 6 ,7, 18, 21, 3, 4, 5
}
