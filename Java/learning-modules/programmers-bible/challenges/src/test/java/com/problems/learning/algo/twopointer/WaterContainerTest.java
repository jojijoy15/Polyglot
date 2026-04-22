package com.problems.learning.algo.twopointer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WaterContainerTest {

    WaterContainer waterContainer = new WaterContainer();

    @Test
    void mostWaterLessContainers() {
        int maxArea = waterContainer.mostWater(new int[]{1, 4, 2, 3, 2});
        assertThat(maxArea).isEqualTo(6);
    }

    @Test
    void mostWaterMoreContainers() {
        int maxArea = waterContainer.mostWater(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7});
        assertThat(maxArea).isEqualTo(49);
    }
}