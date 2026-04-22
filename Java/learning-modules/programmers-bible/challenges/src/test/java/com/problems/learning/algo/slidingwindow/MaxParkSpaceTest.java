package com.problems.learning.algo.slidingwindow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaxParkSpaceTest {

    MaxParkSpace underTest = new MaxParkSpace();

    /*
        1 - Free
        0 - Occupied
     */
    @Test
    void maxSpace() {
        int[] parkingGrid = {0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1};
        int[] parkingArea = underTest.maxSpace(parkingGrid);
        assertThat(parkingArea).containsExactly(5, 3);
    }

    @Test
    void maxSpaceAtEnd() {
        int[] parkingGrid = {0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0};
        int[] parkingArea = underTest.maxSpace(parkingGrid);
        assertThat(parkingArea).containsExactly(9, 4);
    }

    @Test
    void maxSpaceAllZeros() {
        int[] parkingGrid = {0, 0, 0, 0, 0, 0};
        int[] parkingArea = underTest.maxSpace(parkingGrid);
        assertThat(parkingArea).containsExactly(0, 0);
    }

    @Test
    void maxSpaceAllOnes() {
        int[] parkingGrid = {1, 1, 1, 1, 1, 1};
        int[] parkingArea = underTest.maxSpace(parkingGrid);
        assertThat(parkingArea).containsExactly(0, 6);
    }

    @Test
    void maxSpaceZeroAtBeginning() {
        int[] parkingGrid = {0, 1, 1, 1, 1, 1, 1};
        int[] parkingArea = underTest.maxSpace(parkingGrid);
        assertThat(parkingArea).containsExactly(1, 6);
    }

    @Test
    void maxSpaceZeroAtEnd() {
        int[] parkingGrid = {1, 1, 1, 1, 1, 1, 0};
        int[] parkingArea = underTest.maxSpace(parkingGrid);
        assertThat(parkingArea).containsExactly(0, 6);
    }

    @Test
    void maxSpaceZeroAtMiddle() {
        int[] parkingGrid = {1, 1, 1, 0, 1, 1, 1};
        int[] parkingArea = underTest.maxSpace(parkingGrid);
        assertThat(parkingArea).containsExactly(0, 3);
    }
}