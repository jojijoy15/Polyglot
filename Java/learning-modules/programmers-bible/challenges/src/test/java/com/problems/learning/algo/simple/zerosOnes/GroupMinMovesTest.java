package com.problems.learning.algo.simple.zerosOnes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GroupMinMovesTest {

    GroupMinMoves groupMinMoves = new GroupMinMoves();

    @Test
    void minMoves() {
        int[] zeroesOnes = {0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0};
        int minMoves = groupMinMoves.minMoves(zeroesOnes);
        assertThat(minMoves).isEqualTo(7);
    }

    @Test
    void minMovesScattered() {
        //2, 5, 8, 12
        int[] zeroesOnes = {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0};
        int minMoves = groupMinMoves.minMoves(zeroesOnes);
        assertThat(minMoves).isEqualTo(9);
    }
}