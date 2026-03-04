package com.problems.learning.algo.interesting;

public class TrappingWater {

    public int findAmountOfWaterTrapped(int[] heights) {

        // Find Left Max
        int leftMax = 0;
        int[] leftMaxes = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            leftMaxes[i] = leftMax;
            leftMax = Math.max(leftMax, heights[i]);
        }

        // Find Right Max
        int[] rightMaxes = new int[heights.length];
        int rightMax = 0;
        for (int i = heights.length - 1; i >= 0; i--) {
            rightMaxes[i] = rightMax;
            rightMax = Math.max(rightMax, heights[i]);
        }

        int[] trappedWater = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            int minHeight = Math.min(leftMaxes[i], rightMaxes[i]);
            int trapped = minHeight - heights[i];
            trappedWater[i] = Math.max(trapped, 0);
        }

        int totalWater = 0;
        for (int i = 0; i < heights.length; i++) {
            totalWater += trappedWater[i];
        }
        return totalWater;
    }

    public int findAmountOfWaterTrappedTwoPointer(int[] heights) {

        int left = 0;
        int right = heights.length - 1;

        int leftMax = heights[left];
        int rightMax = heights[right];

        int result = 0;
        while (left < right) {
            if (leftMax < rightMax) {
                left++;
                leftMax = Math.max(leftMax, heights[left]);
                result += leftMax - heights[left]; // calculates water accumulated in one location
            } else {
                right--;
                rightMax = Math.max(rightMax, heights[right]);
                result += rightMax - heights[right];
            }
        }
        return result;
    }
}
