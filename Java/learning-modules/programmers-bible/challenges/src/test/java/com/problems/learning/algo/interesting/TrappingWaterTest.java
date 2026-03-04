package com.problems.learning.algo.interesting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrappingWaterTest {

    TrappingWater trappingWater = new TrappingWater();

    @Test
    void findAmountOfWaterTrapped() {
        int[] blockHeights = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int amountOfWaterTrapped = trappingWater.findAmountOfWaterTrapped(blockHeights);
        assertThat(amountOfWaterTrapped).isEqualTo(6);
    }

    @Test
    void findAmountOfWaterTrappedTwoPointer() {
        int[] blockHeights = {4,2,0,3,2,5};
        int amountOfWaterTrapped = trappingWater.findAmountOfWaterTrappedTwoPointer(blockHeights);
        assertThat(amountOfWaterTrapped).isEqualTo(9);

    }
}