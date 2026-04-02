package com.problems.learning.algo.hard.subarray;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinimumSizeSubArrayTest {

    private MinimumSizeSubArray test = new MinimumSizeSubArray();

    @Test
    void findMinLength6() {
        int[] nums = new int[]{1, 2, 3, 4};
        int minLength = test.findMinLength(nums, 6);
        assertThat(minLength).isEqualTo(2);
    }

    @Test
    void findMinLength18() {
        int[] nums = new int[]{16, 1, 17, 11, 5, 2, 12};
        int minLength = test.findMinLength(nums, 18);
        assertThat(minLength).isEqualTo(2);
    }


}