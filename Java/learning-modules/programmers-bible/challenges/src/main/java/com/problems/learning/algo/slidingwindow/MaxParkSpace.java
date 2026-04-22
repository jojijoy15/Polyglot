package com.problems.learning.algo.slidingwindow;

public class MaxParkSpace {

    public int[] maxSpace(int[] parkingGrid) {

        int left = 0;
        int bestStart = 0;
        int maxSum = 0;
        for (int right = 0, sum = 0; right < parkingGrid.length; right++) {
            sum += parkingGrid[right];
            if (sum > maxSum) {
                maxSum = sum;
                bestStart = left;
            }
            if(parkingGrid[right] == 0){
                sum = 0;
                left = right + 1;
            }
        }
        return new int[]{bestStart, maxSum};
    }
}
