package com.problems.learning.algo.dp.paths;

import java.util.Arrays;

public class NoObstaclesUniquePaths {

  // DP approach - O(m*n) time, O(m*n) space
  public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];

    // First row - only one way to reach any cell (move right)
    for (int j = 0; j < n; j++) {
      dp[0][j] = 1;
    }

    // First column - only one way to reach any cell (move down)
    for (int i = 0; i < m; i++) {
      dp[i][0] = 1;
    }

    // Fill the rest of the table
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = dp[i - 1][j] + dp[i][j - 1]; //Current r, c ➡️ add previous row and column value
      }
    }

    Arrays.stream(dp).forEach(e -> System.out.println(Arrays.toString(e)));
    return dp[m - 1][n - 1];
  }

  // Space-optimized approach - O(m*n) time, O(n) space
  public int uniquePathsOptimized(int m, int n) {
    int[] dp = new int[n];
    Arrays.fill(dp, 1);

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[j] += dp[j - 1];
      }
      System.out.println(Arrays.toString(dp));
    }

    return dp[n - 1];
  }
}

