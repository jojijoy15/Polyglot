package com.problems.learning.algo.sorting;

import com.problems.learning.tags.Medium;

@Medium
public class RearrangeArray {

    // Given elements in unsorted order, put them in correct position as per index
    public static void rearrange(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        int n = arr.length;

        for (int i = 0; i < n; i++) {
            // Keep swapping until the current position holds its matching value or a -1
            while (arr[i] != -1 && arr[i] != i) {
                int correctTargetIndex = arr[i];
                
                // Swap the element to its correct index
                int temp = arr[i];
                arr[i] = arr[correctTargetIndex];
                arr[correctTargetIndex] = temp;
            }
        }
    }
}