package com.problems.learning.algo.greedy;

import com.problems.learning.tags.Easy;

@Easy
public class WaveArray {

    public static void convertToWave(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // Traverse only the even elements
        for (int i = 0; i < n; i += 2) {
            
            // If the current even element is smaller than the previous odd element, swap
            if (i > 0 && arr[i] < arr[i - 1]) { // Down slope
                swap(arr, i, i - 1);
            }
            
            // If the current even element is smaller than the next odd element, swap
            if (i < n - 1 && arr[i] < arr[i + 1]) { // Up slope
                swap(arr, i, i + 1);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}