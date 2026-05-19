package com.problems.learning.algo.math;

public class MaxCircularSum {

    //Given an array, you need to find the maximum sum of arr[i]* (i+1) for all elements,
    // such that you are allowed to rotate the array.
    public static int maxRotateSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        int n = arr.length;
        int S = 0;         // Sum of all elements
        int currentP = 0;  // Current sum of arr[i] * (i + 1)
        
        // Step 1: Calculate the base case (0th rotation) and total sum
        for (int i = 0; i < n; i++) {
            S += arr[i];
            currentP += arr[i] * (i + 1);
        }
        
        int maxP = currentP;
        
        // Step 2: Try all N-1 remaining rotations using the O(1) math trick
        for (int i = 0; i < n - 1; i++) {
            // arr[i] is the element shifting from the front to the back
            currentP = currentP + (n * arr[i]) - S;
            
            // Update max if the new rotation yields a higher sum
            maxP = Math.max(maxP, currentP);
        }
        
        return maxP;
    }

}