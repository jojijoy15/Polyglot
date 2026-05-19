package com.problems.learning.algo.twopointer;

public class MaxIndexDistance {

    //Problem: Given an array arr[], find the maximum distance of the index j and i, such that arr[j] > arr[i]
    public static int maxIndexDiff(int[] arr) {
        if (arr == null || arr.length < 2) {
            return -1; // No valid pair can exist
        }
        
        int n = arr.length;
        int[] LMin = new int[n];
        int[] RMax = new int[n];

        // Construct LMin[] such that LMin[i] stores the minimum value 
        // from arr[0] to arr[i]
        LMin[0] = arr[0];
        for (int i = 1; i < n; i++) {
            LMin[i] = Math.min(arr[i], LMin[i - 1]);
        }

        // Construct RMax[] such that RMax[j] stores the maximum value 
        // from arr[j] to arr[n-1]
        RMax[n - 1] = arr[n - 1];
        for (int j = n - 2; j >= 0; j--) {
            RMax[j] = Math.max(arr[j], RMax[j + 1]);
        }

        // Traverse both arrays from left to right to find optimum j - i
        int i = 0;
        int j = 0;
        int maxDiff = -1;

        while (i < n && j < n) {
            if (LMin[i] < RMax[j]) {
                maxDiff = Math.max(maxDiff, j - i);
                j++; // Expand the window to the right
            } else {
                i++; // Shrink the window from the left
            }
        }

        return maxDiff;
    }

}