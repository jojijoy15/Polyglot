package com.problems.learning.algo.search.binarysearch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindPeakTest {

    FindPeak findPeak = new FindPeak();

    @Test
    void findPeak() {
        int[] mountain = {1, 2, 3, 1};
        int peakAt = findPeak.findPeak(mountain);
        assertThat(peakAt).isEqualTo(2);
    }

    @Test
    void findPeakMoreValley() {
        int[] mountain = {1, 2, 1, 3, 5, 6, 4};
        int peakAt = findPeak.findPeak(mountain);
        assertThat(peakAt).isEqualTo(5);
    }

    @Test
    void findPeakNoValley() {
        int[] mountain = {10};
        int peakAt = findPeak.findPeak(mountain);
        assertThat(peakAt).isEqualTo(0);
    }
}