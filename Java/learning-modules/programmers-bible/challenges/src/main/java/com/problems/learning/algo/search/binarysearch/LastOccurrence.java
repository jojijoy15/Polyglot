package com.problems.learning.algo.search.binarysearch;

public class LastOccurrence {

    /*
        Sorted array with duplicate elements, return last occurrence
    */
    public int foundLastAt(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                while (mid < arr.length && arr[mid] == target) {
                    mid = mid + 1;
                }
                return mid - 1;
            } else if (arr[mid] < target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
