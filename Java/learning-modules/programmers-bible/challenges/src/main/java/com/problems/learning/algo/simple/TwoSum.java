package com.problems.learning.algo.simple;

import java.util.*;

public class TwoSum {

    /**
     * Classic Two Sum — return indices of the ONE pair that sums to target.
     * Time: O(n), Space: O(n)
     */
    public int[] twoSumIndices(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }
            seen.put(nums[i], i);
        }
        return new int[]{};
    }

    /**
     * Two Sum with ALL pairs — return all unique pairs (values) that sum to target.
     * Each element can only be used once across all pairs.
     *
     * Example: nums = [1, 2, 3, 4, 5, 6, 3, 4], target = 7
     * Output: [[1,6], [2,5], [3,4], [3,4]]
     *
     * Time: O(n log n), Space: O(n)
     */
    public List<int[]> twoSumAllPairs(int[] nums, int target) {
        List<int[]> result = new ArrayList<>();
        Arrays.sort(nums);

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum == target) {
                result.add(new int[]{nums[left], nums[right]});
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return result;
    }

    /**
     * Two Sum ALL UNIQUE pairs — return only distinct pairs (no duplicate pairs).
     * Each element can only be used once, and duplicate pairs are skipped.
     *
     * Example: nums = [1, 2, 3, 4, 5, 6, 3, 4], target = 7
     * Output: [[1,6], [2,5], [3,4]]  (only one [3,4] pair)
     *
     * Time: O(n log n), Space: O(n)
     */
    public List<int[]> twoSumUniquePairs(int[] nums, int target) {
        List<int[]> result = new ArrayList<>();
        Arrays.sort(nums);

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum == target) {
                result.add(new int[]{nums[left], nums[right]});

                // Skip duplicate values from left
                while (left < right && nums[left] == nums[left + 1]) left++;
                // Skip duplicate values from right
                while (left < right && nums[right] == nums[right - 1]) right--;

                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return result;
    }
}

