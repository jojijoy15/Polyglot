package com.problems.learning.algo.hard.subarray;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinMaxSubarraySumTest {
    MinMaxSubarraySum solver = new MinMaxSubarraySum();

    @Test
    void givenExample() {
        // [4,3,-1,7] -> min=-1, max=7 -> 6
        // [3,-1,7,0] -> min=-1, max=7 -> 6
        // [-1,7,0,3] -> min=-1, max=7 -> 6
        // [7,0,3,-3] -> min=-3, max=7 -> 4
        // [0,3,-3,2] -> min=-3, max=3 -> 0
        // Total: 6+6+6+4+0 = 22
        int[] arr = {4, 3, -1, 7, 0, 3, -3, 2};
        assertThat(solver.sumOfMinMaxUsingTreeMap(arr, 4)).isEqualTo(22);
    }

    @Test
    void allPositive() {
        // [2,5,1] -> 1+5=6, [5,1,8] -> 1+8=9, [1,8,3] -> 1+8=9
        // Total: 6+9+9 = 24
        int[] arr = {2, 5, 1, 8, 3};
        assertThat(solver.sumOfMinMaxUsingTreeMap(arr, 3)).isEqualTo(24);
    }

    @Test
    void windowEqualsArraySize() {
        // Single window: min=-1, max=7 -> 6
        int[] arr = {4, 3, -1, 7};
        assertThat(solver.sumOfMinMaxUsingTreeMap(arr, 4)).isEqualTo(6);
    }

    @Test
    void windowSizeOne() {
        // Each element is its own min and max
        // (4+4)+(3+3)+(-1+-1)+(7+7) = 8+6-2+14 = 26
        int[] arr = {4, 3, -1, 7};
        assertThat(solver.sumOfMinMaxUsingTreeMap(arr, 1)).isEqualTo(26);
    }

    @Test
    void allSameElements() {
        // [5,5,5] -> 5+5=10, three windows -> 30
        int[] arr = {5, 5, 5, 5, 5};
        assertThat(solver.sumOfMinMaxUsingTreeMap(arr, 3)).isEqualTo(30);
    }

    @Test
    void allNegative() {
        // [-4,-3,-1] -> -4+-1=-5, [-3,-1,-7] -> -7+-1=-8
        // Total: -5 + -8 = -13
        int[] arr = {-4, -3, -1, -7};
        assertThat(solver.sumOfMinMaxUsingTreeMap(arr, 3)).isEqualTo(-13);
    }

    @Test
    void emptyArray() {
        assertThat(solver.sumOfMinMaxUsingTreeMap(new int[]{}, 3)).isEqualTo(0);
    }

    @Test
    void kLargerThanArray() {
        assertThat(solver.sumOfMinMaxUsingTreeMap(new int[]{1, 2}, 5)).isEqualTo(0);
    }
}
