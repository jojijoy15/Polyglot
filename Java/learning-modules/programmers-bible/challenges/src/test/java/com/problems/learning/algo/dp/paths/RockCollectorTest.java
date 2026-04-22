package com.problems.learning.algo.dp.paths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RockCollectorTest {

    RockCollector rockCollector = new RockCollector();

    @Test
    void collectMaxRocksSouthToNorthWay() {
        int[][] grid = {
            {0, 2, 2, 1},
            {3, 1, 1, 1},
            {4, 0, 2, 0}
        };
        int actual = rockCollector.collectMaxRocksSouthToNorthWay(grid);
        assertEquals(13, actual);
    }
}