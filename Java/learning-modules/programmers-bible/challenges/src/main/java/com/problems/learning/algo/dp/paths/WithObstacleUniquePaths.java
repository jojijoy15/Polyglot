package com.problems.learning.algo.dp.paths;

public class WithObstacleUniquePaths {

    /*
        1 -> Obstacle
        0 -> Safe path
     */
    public int uniquePaths(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[][] dp = new int[rows][columns];

        for (int i = rows - 1; i > -1; i--) {
            if (grid[i][0] == 1) {
                dp[i][0] = 0;
            } else {
                dp[i][0] = 1;
            }
        }

        for (int j = columns - 1; j > -1; j--) {
            if (grid[0][j] == 1) {
                dp[0][j] = 0;
            } else {
                dp[0][j] = 1;
            }
        }

        for (int i = rows - 2; i > -1; i--) {
            for (int j = columns - 2; j > -1; j--) {
                if (grid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
                }
            }
        }
        return dp[0][0];
    }

    public int uniquePathsOptimized(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[] dp = new int[columns];
        dp[columns - 1] = 1;
        for (int i = rows - 1; i > -1; i--) {
            for (int j = columns - 1; j > -1; j--) {
                if (grid[i][j] == 1){
                    dp[j] = 0;
                } else if (j + 1 <  columns) {
                    dp[j] = dp[j] + dp[j + 1];
                }
            }
        }
        return dp[0];
    }
}