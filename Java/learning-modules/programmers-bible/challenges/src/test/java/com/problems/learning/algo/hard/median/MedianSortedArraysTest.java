package com.problems.learning.algo.hard.median;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedianSortedArraysTest {

    MedianSortedArrays medianSortedArrays = new MedianSortedArrays();

    @Test
    void findMedian() {
        int[] first = {1, 4, 5, 6, 9, 10, 23};
        int[] second = {1, 2, 7, 11, 20, 23};
        double median = medianSortedArrays.findMedian(first, second);
        assertThat(median).isEqualTo(7);
    }
}