package com.problems.learning.algo.string.subsequence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongestRepeatingSubSequenceTest {

    LongestRepeatingSubSequence lrs = new LongestRepeatingSubSequence();

    @Test
    void findLongestRepeatingSubSequence() {
        String sequence = "AABEBCDD";
        int length = lrs.longestRepeatingSubsequence(sequence);
        assertThat(length).isEqualTo(3);
    }

}