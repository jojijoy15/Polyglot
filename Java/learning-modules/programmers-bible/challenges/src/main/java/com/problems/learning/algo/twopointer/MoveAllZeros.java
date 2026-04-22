package com.problems.learning.algo.twopointer;

public class MoveAllZeros {

    public void moveZeroesToBack(int[] nums) {
        if (nums == null) return;
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0)
                nums[k++] = nums[i];
        }
        while (k < nums.length)
            nums[k++] = 0;
    }

    public void moveZeroesToFront(int[] nums) {
        if (nums == null) return;
        int k = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] != 0)
                nums[k--] = nums[i];
        }
        while (k >= 0)
            nums[k--] = 0;
    }
}
