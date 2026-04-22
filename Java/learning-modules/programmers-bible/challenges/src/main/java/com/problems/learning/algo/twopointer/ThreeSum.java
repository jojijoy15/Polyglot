package com.problems.learning.algo.twopointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        for(int i = 0; i < nums.length; ++i) {
            if (nums[i] > 0) 
                break;
            if (i > 0 && nums[i] == nums[i - 1]) 
                continue;
            int right = nums.length - 1;
            int left = i + 1;
            while(left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if(sum > 0) {
                    right--;
                }
                else if( sum < 0) {
                    left++;
                }
                else {
                    list.add(List.of(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                }
 
            }
        }
        return list;
    }
}