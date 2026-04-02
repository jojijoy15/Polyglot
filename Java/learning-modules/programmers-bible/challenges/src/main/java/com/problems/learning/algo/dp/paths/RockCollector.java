package com.problems.learning.algo.dp.paths;

import java.util.Arrays;

public class RockCollector {

    public int collectMaxRocksSouthToNorthWay(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] dp = new int[rows][cols];

        dp[rows - 1][0] = grid[rows - 1][0]; // fill the south west value
        for (int c = 1; c < cols; c++) {
            dp[rows - 1][c] = dp[rows - 1][c - 1] + grid[rows - 1][c];
        }

        for (int r = rows - 2; r >= 0; r--) {
            dp[r][0] = dp[r + 1][0] + grid[r][0];
        }

        Arrays.stream(dp).forEach(
                e -> System.out.println(Arrays.toString(e))
        );
        for (int r = rows - 2; r >= 0; r--) {
            for (int c = 1; c < cols; c++) {
                dp[r][c] = grid[r][c] + Math.max(dp[r][c - 1], dp[r + 1][c]);
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[0][cols - 1];
    }
}
