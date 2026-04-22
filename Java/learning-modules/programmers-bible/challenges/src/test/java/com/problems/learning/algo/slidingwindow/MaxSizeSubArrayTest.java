package com.problems.learning.algo.slidingwindow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaxSizeSubArrayTest {

    MaxSizeSubArray maxSizeSubArray = new MaxSizeSubArray();

    @Test
    void findMaxLengthForTargetSum() {
        int[] arr = {1, -1, 5, -2, 0, -1, 1, 3};
        int target = 3;
        int[] sub = maxSizeSubArray.findMaxLengthForTargetSum(arr, target);
        assertThat(sub).containsExactly(1, -1, 5, -2, 0, -1, 1);
    }

    @Test
    void findMaxLengthForTargetSum3() {
        int[] arr = {3, 1, -1, 5, -2, 0, -1, 1};
        int target = 3;
        int[] sub = maxSizeSubArray.findMaxLengthForTargetSum(arr, target);
        assertThat(sub).containsExactly(1, -1, 5, -2, 0, -1, 1);
    }

}