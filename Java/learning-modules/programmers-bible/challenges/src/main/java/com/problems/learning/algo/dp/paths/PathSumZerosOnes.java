package com.problems.learning.algo.dp.paths;

import java.util.Arrays;

public class PathSumZerosOnes {

    /*
    You are given an m × n grid filled with 0 and 1s.
    Starting from the top-left corner, you need to reach the bottom-right corner.
    At each step, you can only move either right or down.
    Your task is to find the max possible sum of all numbers along a path from the start to the destination.

    Example
        Input: [ [0, 0, 0], [0, 1, 0], [1, 1, 0] ]
        Output: 2
    */

    public int maxSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] dp = new int[rows + 1][cols + 1];
        // fill last column with max value
        for (int i = 0; i < rows + 1; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[rows - 1][cols] = 0; // VI for base case

        for(int row = rows - 1; row > -1 ; row--){
            for(int col = cols - 1; col > -1; col--) {
                dp[row][col] = grid[row][col] + Math.max( dp[row + 1][col], dp[row][col + 1]);
            }
        }
        Arrays.stream(dp).forEach(e -> System.out.println(Arrays.toString(e)));
        return dp[0][0];
    }

    public int maxSumOptimized(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[] dp = new int[cols + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[cols - 1] = 0;  // VImp for base case

        for(int row = rows - 1; row > -1 ; row--){
            for(int col = cols - 1; col > -1; col--) {
                dp[col] = grid[row][col] + Math.max(dp[col], dp[col+1]);
            }
            System.out.println(Arrays.toString(dp));
        }
        return dp[0];
    }
}
