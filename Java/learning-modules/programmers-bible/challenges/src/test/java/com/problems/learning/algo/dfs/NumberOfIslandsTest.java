package com.problems.learning.algo.dfs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfIslandsTest {

    private final NumberOfIslands solution = new NumberOfIslands();

    @Test
    void testSingleIsland() {
        char[][] grid = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        assertEquals(1, solution.numIslands(grid));
    }

    @Test
    void testMultipleIslands() {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        assertEquals(3, solution.numIslands(grid));
    }

    @Test
    void testAllWater() {
        char[][] grid = {
                {'0', '0', '0'},
                {'0', '0', '0'}
        };
        assertEquals(0, solution.numIslands(grid));
    }

    @Test
    void testAllLand() {
        char[][] grid = {
                {'1', '1'},
                {'1', '1'}
        };
        assertEquals(1, solution.numIslands(grid));
    }

    @Test
    void testEmptyGrid() {
        assertEquals(0, solution.numIslands(new char[][]{}));
    }
}

