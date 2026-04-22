package com.problems.learning.algo.dp.paths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinimumPathSumTest {

    MinimumPathSum underTest = new MinimumPathSum();

    @Test
    void minSum() {
        int[][] grid = {
                {1, 3, 1}, {1, 5, 1}, {4, 2, 1}
        };
        int minSum = underTest.minSum(grid);
        assertThat(minSum).isEqualTo(7);
    }

    @Test
    void minSumOptimized() {
        int[][] grid = {
                {1, 3, 1}, {1, 5, 1}, {4, 2, 1}
        };
        int minSum = underTest.minSumOptimized(grid);
        assertThat(minSum).isEqualTo(7);
    }
}