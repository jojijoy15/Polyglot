package com.problems.learning.algo.dp.paths;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WithObstacleUniquePathsTest {

    WithObstacleUniquePaths underTest = new WithObstacleUniquePaths();

    @Test
    void uniquePaths() {
        int[][] grid = {{0,0,0},{0,1,0},{0,0,0}};
        int paths = underTest.uniquePaths(grid);
        assertThat(paths).isEqualTo(2);
    }

    @Test
    void uniquePaths2D() {
        int[][] grid = {{0,1},{0,0}};
        int paths = underTest.uniquePaths(grid);
        assertThat(paths).isEqualTo(1);
    }

    @Test
    void uniquePathsOptimized() {
        int[][] grid = {{0,0,0},{0,1,0},{0,0,0}};
        int paths = underTest.uniquePathsOptimized(grid);
        assertThat(paths).isEqualTo(2);
    }

    @Test
    void uniquePaths2DOptimized() {
        int[][] grid = {{0,1},{0,0}};
        int paths = underTest.uniquePathsOptimized(grid);
        assertThat(paths).isEqualTo(1);
    }

    @Test
    void uniquePathsManyObstacles() {
        int[][] grid = {{0,0,0},{0,1,0},{1,1,0}};
        int paths = underTest.uniquePaths(grid);
        assertThat(paths).isEqualTo(1);
    }

    @Test
    void uniquePathsOptimizedManyObstacles() {
        int[][] grid = {{0,0,0},{0,1,0},{1,1,0}};
        int paths = underTest.uniquePathsOptimized(grid);
        assertThat(paths).isEqualTo(1);
    }

}