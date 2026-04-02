package com.problems.learning.algo.simple.binarysearch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RotatedArrayMinTest {

    @Test
    void rotatedInMiddle() {
        // [4,5,6,1,2,3] → min is 1
        int result = RotatedArrayMin.findMin(new int[]{4, 5, 6, 1, 2, 3});
        assertThat(result).isEqualTo(1);
    }

    @Test
    void rotatedAtSecondElement() {
        // [5,1,2,3,4] → min is 1
        int result = RotatedArrayMin.findMin(new int[]{5, 1, 2, 3, 4});
        assertThat(result).isEqualTo(1);
    }

    @Test
    void rotatedAtLastElement() {
        // [2,3,4,5,1] → min is 1
        int result = RotatedArrayMin.findMin(new int[]{2, 3, 4, 5, 1});
        assertThat(result).isEqualTo(1);
    }

    @Test
    void noRotation() {
        // [1,2,3,4,5] → already sorted, min is 1
        int result = RotatedArrayMin.findMin(new int[]{1, 2, 3, 4, 5});
        assertThat(result).isEqualTo(1);
    }

    @Test
    void twoElementsRotated() {
        // [3,1] → min is 1
        int result = RotatedArrayMin.findMin(new int[]{3, 1});
        assertThat(result).isEqualTo(1);
    }

    @Test
    void twoElementsNotRotated() {
        // [1,3] → min is 1
        int result = RotatedArrayMin.findMin(new int[]{1, 3});
        assertThat(result).isEqualTo(1);
    }

    @Test
    void singleElement() {
        // [7] → min is 7
        int result = RotatedArrayMin.findMin(new int[]{7});
        assertThat(result).isEqualTo(7);
    }

    @Test
    void largeRotation() {
        // [10,20,30,40,50,5] → min is 5
        int result = RotatedArrayMin.findMin(new int[]{10, 20, 30, 40, 50, 5});
        assertThat(result).isEqualTo(5);
    }

    @Test
    void minimalRotation() {
        // [2,1] → min is 1
        int result = RotatedArrayMin.findMin(new int[]{2, 1});
        assertThat(result).isEqualTo(1);
    }
}
