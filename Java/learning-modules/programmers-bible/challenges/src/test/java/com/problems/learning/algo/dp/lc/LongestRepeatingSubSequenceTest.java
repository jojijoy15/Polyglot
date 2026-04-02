package com.problems.learning.algo.dp.lc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongestRepeatingSubSequenceTest {

    LongestRepeatingSubSequence lrs = new LongestRepeatingSubSequence();

    @Test
    void findLongestRepeatingSubSequence() {
        String sequence = "AABEBCDD";
        // ABD
        int length = lrs.longestRepeatingSubsequence(sequence);
        assertThat(length).isEqualTo(3);
    }

}