package com.problems.learning.algo.search.binarysearch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SingleNonRepeatingTest {

    SingleNonRepeating solver = new SingleNonRepeating();

    @Test
    void singleInMiddle() {
        assertThat(solver.findSingle(new int[]{2, 2, 4, 4, 8, 9, 9})).isEqualTo(8);
    }

    @Test
    void singleAtStart() {
        assertThat(solver.findSingle(new int[]{1, 2, 2, 3, 3})).isEqualTo(1);
    }

    @Test
    void singleAtEnd() {
        assertThat(solver.findSingle(new int[]{1, 1, 2, 2, 5})).isEqualTo(5);
    }

    @Test
    void singleElement() {
        assertThat(solver.findSingle(new int[]{7})).isEqualTo(7);
    }

    @Test
    void threeElements() {
        assertThat(solver.findSingle(new int[]{1, 1, 3})).isEqualTo(3);
    }

    @Test
    void largerArray() {
        assertThat(solver.findSingle(new int[]{1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6})).isEqualTo(4);
    }
}

