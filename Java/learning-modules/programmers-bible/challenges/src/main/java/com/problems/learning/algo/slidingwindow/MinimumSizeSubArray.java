package com.problems.learning.algo.slidingwindow;

/*
    Write a function that takes in an array and a integer.
    You want to return the *LENGTH* of the shortest subarray
    whose sum is at least the integer, and -1 if no such sum exists.

        input: [1, 2, 3, 4], k=6, Output: 2
        input: [1, 2, 3, 4], k=12, Output: -1
*/
public class MinimumSizeSubArray {

    public int findMinLength(int[] nums, int k) {
        int length = nums.length;
        int left = 0;
        int sum = 0;
        int subMinLength = Integer.MAX_VALUE;
        for(int right = 0; right < length; right++) {
            sum += nums[right];
            while (sum >= k) {
                subMinLength = Math.min(subMinLength, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return subMinLength == Integer.MAX_VALUE ? -1 : subMinLength;
    }
}
