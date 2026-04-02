package com.problems.learning.algo.dp.paths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoObstaclesUniquePathsTest {

    NoObstaclesUniquePaths noObstaclesUniquePaths = new NoObstaclesUniquePaths();

    @Test
    void uniquePaths() {
        int paths = noObstaclesUniquePaths.uniquePaths(3, 2);
        assertThat(paths).isEqualTo(3);
    }

    @Test
    void uniquePathsBigSpace() {
        int paths = noObstaclesUniquePaths.uniquePaths(3, 7);
        assertThat(paths).isEqualTo(28);
    }

    @Test
    void uniquePathsOptimized() {
        int paths = noObstaclesUniquePaths.uniquePathsOptimized(3, 2);
        assertThat(paths).isEqualTo(3);
    }

    @Test
    void uniquePathsOptimizedBigSpace() {
        int paths = noObstaclesUniquePaths.uniquePathsOptimized(3, 7);
        assertThat(paths).isEqualTo(28);
    }
}