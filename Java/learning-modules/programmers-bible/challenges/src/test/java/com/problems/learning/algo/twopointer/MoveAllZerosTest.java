package com.problems.learning.algo.twopointer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoveAllZerosTest {

    MoveAllZeros moveAllZeros = new MoveAllZeros();

    @Test
    void moveZeroesToFront() {
        int[] input = new int[]{0, 1, 1, 1, 0, 0, 0, 1, 1};
        moveAllZeros.moveZeroesToFront(input);
        assertThat(input).containsExactly(0, 0, 0, 0, 1, 1, 1, 1, 1);
    }

    @Test
    void moveZeroesToFrontReverse() {
        int[] input = new int[]{1, 1, 1, 0};
        moveAllZeros.moveZeroesToFront(input);
        assertThat(input).containsExactly(0, 1, 1, 1);
    }

    @Test
    void moveZeroesToFrontEmpty() {
        int[] input = new int[]{};
        moveAllZeros.moveZeroesToFront(input);
        assertThat(input).containsExactly();
    }

    @Test
    void moveZeroesToBack() {
        int[] input = new int[]{0, 1, 1, 1, 0, 0, 0, 1, 1};
        moveAllZeros.moveZeroesToBack(input);
        assertThat(input).containsExactly(1, 1, 1, 1, 1, 0, 0, 0, 0);
    }

    @Test
    void moveZeroesToBackReverse() {
        int[] input = new int[]{0, 1, 1, 1};
        moveAllZeros.moveZeroesToBack(input);
        assertThat(input).containsExactly(1, 1, 1, 0);
    }

    @Test
    void moveZeroesToBackEmpty() {
        int[] input = new int[]{};
        moveAllZeros.moveZeroesToBack(input);
        assertThat(input).containsExactly();
    }
}