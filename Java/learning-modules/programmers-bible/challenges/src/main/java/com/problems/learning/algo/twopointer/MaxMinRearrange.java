package com.problems.learning.algo.twopointer;

import com.problems.learning.tags.Medium;

@Medium
public class MaxMinRearrange {

    public void rearrange(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;
        
        // Pointers to the current max and min elements
        int maxIdx = n - 1;
        int minIdx = 0;

        // The key element used to store two values in one index.
        // It must be strictly greater than any element in the array.
        int maxElem = arr[n - 1] + 1;

        // Traverse array elements
        for (int i = 0; i < n; i++) {
            // At even indices, we want to put maximum elements
            if (i % 2 == 0) {
                arr[i] += (arr[maxIdx] % maxElem) * maxElem;
                maxIdx--;
            } 
            // At odd indices, we want to put minimum elements
            else {
                arr[i] += (arr[minIdx] % maxElem) * maxElem;
                minIdx++;
            }
        }

        // Divide all elements by maxElem to extract the new values
        for (int i = 0; i < n; i++) {
            arr[i] = arr[i] / maxElem;
        }
    }
}