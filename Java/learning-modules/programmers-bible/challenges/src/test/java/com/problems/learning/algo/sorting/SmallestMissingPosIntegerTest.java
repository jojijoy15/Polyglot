package com.problems.learning.algo.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SmallestMissingPosIntegerTest {

    SmallestMissingPosInteger instance = new SmallestMissingPosInteger();

    @Test
    void findMissingNumber() {
        int[] elements = {1, 2, 0};
        int missingNumber = instance.findMissingNumber(elements);
        assertThat(missingNumber).isEqualTo(3);
    }

    @Test
    void findMissingNumberWithNegativeNumber() {
        int[] elements = {3, 4, 1, -1};
        int missingNumber = instance.findMissingNumber(elements);
        assertThat(missingNumber).isEqualTo(2);
    }

    @Test
    void findMissingNumberWithDuplicateNumbers() {
        int[] elements = {7, 4, 9, 1, 4};
        int missingNumber = instance.findMissingNumber(elements);
        assertThat(missingNumber).isEqualTo(2);
    }


}