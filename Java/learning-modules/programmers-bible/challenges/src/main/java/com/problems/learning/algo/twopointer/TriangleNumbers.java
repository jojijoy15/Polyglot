package com.problems.learning.algo.twopointer;

import java.util.Arrays;

public class TriangleNumbers {

    /*
    Space: O(1)
    Time : O(n^2)
     */
    public Integer triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = nums.length - 1; i >= 2; i--) {
            int left = 0;
            int right = i - 1;
            while (left < right) {
                if (nums[left] + nums[right] > nums[i]) {
                    count += right - left;
                    right--;
                } else {
                    left++;
                }
            }
        }

        return count;
    }
}