package com.problems.learning.algo.dp.paths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PathSumZerosOnesTest {

    PathSumZerosOnes pathSumZerosOnes = new PathSumZerosOnes();
    
    @Test
    void maxSum() {
        int[][] grid = {
                {0, 0, 0},
                {0, 1, 0},
                {1, 1, 0}
        };
        int maxSum = pathSumZerosOnes.maxSum(grid);
        assertThat(maxSum).isEqualTo(2);
    }

    @Test
    void maxSumOptimized() {
        int[][] grid = {
                {0, 0, 0},
                {0, 1, 0},
                {1, 1, 0}
        };
        int maxSum = pathSumZerosOnes.maxSumOptimized(grid);
        assertThat(maxSum).isEqualTo(2);
    }
}