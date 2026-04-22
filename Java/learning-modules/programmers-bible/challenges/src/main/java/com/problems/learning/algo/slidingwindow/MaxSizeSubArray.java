package com.problems.learning.algo.slidingwindow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MaxSizeSubArray {

    public int[] findMaxLengthForTargetSum(int[] nums, int target) {
        Map<Integer, Integer> prefixMap = new HashMap<>();
        int sum = 0;

        int maxLen = 0;
        int start = -1;
        int end = -1;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            // Case 1: subarray from 0 to i
            if (sum == target) {
                if (i + 1 > maxLen) {
                    maxLen = i + 1;
                    start = 0;
                    end = i;
                }
            }

            // Case 2: subarray in middle
            if (prefixMap.containsKey(sum - target)) {
                int prevIndex = prefixMap.get(sum - target);
                int length = i - prevIndex;

                if (length > maxLen) {
                    maxLen = length;
                    start = prevIndex + 1;
                    end = i;
                }
            }

            // Store only first occurrence
            prefixMap.putIfAbsent(sum, i); // Longest
        }

        if (maxLen == 0) return new int[0];

        return Arrays.copyOfRange(nums, start, end + 1);
    }
}
