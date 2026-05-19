package com.problems.learning.algo.twopointer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MaxMinRearrangeTest {

    private MaxMinRearrange maxMinRearrange = new MaxMinRearrange();

    @Test
    void rearrangeOdd() {
        int[] elements = new int[] {1,2,3,4,5};
        maxMinRearrange.rearrange(elements);
        assertThat(elements).containsExactly(5, 1, 4, 2, 3);
    }

    @Test
    void rearrangeEven() {
        int[] elements = new int[] {1,2,3,4,5, 6};
        maxMinRearrange.rearrange(elements);
        assertThat(elements).containsExactly(6, 1, 5, 2, 4, 3);
    }
}