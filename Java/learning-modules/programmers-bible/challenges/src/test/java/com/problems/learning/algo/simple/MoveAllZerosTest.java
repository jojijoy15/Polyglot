package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoveAllZerosTest {

    MoveAllZeros moveAllZeros = new MoveAllZeros();

    @Test
    void moveZeroesToFront() {
        int[] input = new int[]{0, 1, 1, 1, 0, 0, 0, 1, 1};
        int[] output = moveAllZeros.moveZeroesToFront(input);
        assertThat(output).containsExactly(0, 0, 0, 0, 1, 1, 1, 1, 1);
    }

    @Test
    void moveZeroesToFrontReverse() {
        int[] input = new int[]{1, 1, 1, 0};
        int[] output = moveAllZeros.moveZeroesToFront(input);
        assertThat(output).containsExactly(0, 1, 1, 1);
    }

    @Test
    void moveZeroesToFrontEmpty() {
        int[] input = new int[]{};
        int[] output = moveAllZeros.moveZeroesToFront(input);
        assertThat(output).containsExactly();
    }

    @Test
    void moveZeroesToBack() {
        int[] input = new int[]{0, 1, 1, 1, 0, 0, 0, 1, 1};
        int[] output = moveAllZeros.moveZeroesToBack(input);
        assertThat(output).containsExactly(1, 1, 1, 1, 1, 0, 0, 0, 0);
    }

    @Test
    void moveZeroesToBackReverse() {
        int[] input = new int[]{0, 1, 1, 1};
        int[] output = moveAllZeros.moveZeroesToBack(input);
        assertThat(output).containsExactly(1, 1, 1, 0);
    }

    @Test
    void moveZeroesToBackEmpty() {
        int[] input = new int[]{};
        int[] output = moveAllZeros.moveZeroesToBack(input);
        assertThat(output).containsExactly();
    }
}