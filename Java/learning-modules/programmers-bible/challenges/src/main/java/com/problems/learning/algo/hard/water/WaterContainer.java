package com.problems.learning.algo.hard.water;

public class WaterContainer {

    public int mostWater(int[] waterContainer) {
        int result = 0;
        int left = 0;
        int right = waterContainer.length - 1;

        while (left <= right) {
            int area = (right - left) * Math.min(waterContainer[left], waterContainer[right]);
            result = Math.max(area, result);
            if(waterContainer[left] <= waterContainer[right]) {
                left++;
            } else {
                right--;
            }
        }
        return result;
    }
}
