package com.problems.learning.algo.twopointer;

import com.problems.learning.tags.Medium;

@Medium
public class MaxSumPath {

    public static int maxPathSum(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null) {
            return 0;
        }

        int m = arr1.length;
        int n = arr2.length;
        
        int i = 0, j = 0;
        int sum1 = 0, sum2 = 0, totalResult = 0;

        // Traverse both arrays
        while (i < m && j < n) {
            if (arr1[i] < arr2[j]) {
                sum1 += arr1[i++];
            } else if (arr1[i] > arr2[j]) {
                sum2 += arr2[j++];
            } else {
                // Intersection point found
                totalResult += Math.max(sum1, sum2) + arr1[i];
                
                // Reset temporary sums
                sum1 = 0;
                sum2 = 0;
                
                // Move both pointers forward
                i++;
                j++;
            }
        }

        // Add remaining elements of arr1
        while (i < m) {
            sum1 += arr1[i++];
        }

        // Add remaining elements of arr2
        while (j < n) {
            sum2 += arr2[j++];
        }

        // Add the maximum of the remaining sums to the result
        totalResult += Math.max(sum1, sum2);

        return totalResult;
    }

}