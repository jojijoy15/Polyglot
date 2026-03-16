package com.problems.learning.algo.interesting;

public class RockCollector {

    public int collectMaxRocksSouthToNorthWay(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] dp = new int[rows][cols];

        dp[rows-1][0] = grid[rows-1][0];
        for(int c = 1; c < cols; c++){
            dp[rows-1][c] = dp[rows - 1][c - 1] + grid[rows-1][c];
        }

        for(int r = rows - 2; r >= 0; r--){
            dp[r][0] = dp[r + 1][0] + grid[r][0];
        }

       for(int r = rows - 2; r >= 0; r--) {
           for (int c = 1; c < cols; c++) {
               dp[r][c] = grid[r][c] + Math.max(dp[r][c - 1], dp[r + 1][c]);
           }
       }
       return dp[0][cols - 1];
    }
}
