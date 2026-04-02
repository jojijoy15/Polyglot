package com.problems.learning.algo.simple;

public class MinSwaps {

    public int minSwaps(int[] arr) {
        int n = arr.length;

        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;

        int minIndex = -1;
        int maxIndex = -1;

        for (int i = 0; i < n; i++) {
            if (arr[i] < minVal) {
                minVal = arr[i];
                minIndex = i;
            }
            if (arr[i] >= maxVal) {
                maxVal = arr[i];
                maxIndex = i;
            }
        }

        int swaps = minIndex + (n - 1 - maxIndex);

        // Adjust overlap
        return maxIndex < minIndex ? --swaps : swaps;
    }
}