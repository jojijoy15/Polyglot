package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinSwapsTest {

    MinSwaps minSwaps = new MinSwaps();

    @Test
    void minSwaps() {
        int swaps = minSwaps.minSwaps(new int[]{3, 1, 5, 2, 4});
        assertThat(swaps).isEqualTo(3);
    }
}