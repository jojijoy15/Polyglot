package com.problems.learning.algo.search.binarysearch;

public class FindPeak {

    /*
        You are given an array of integers where a peak element is an element that is strictly greater than its neighbors.
        Return the index of any one peak element using Binary Search.
        You may assume that the array contains at least one peak.
     */
    public int findPeak(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < arr[mid + 1]) { // slope increasing go right
                left = mid + 1;
            } else {
                right = mid;                // slope decreasing go left
            }
        }
        return left;
    }

}
