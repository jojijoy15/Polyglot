package com.problems.learning.algo.string.subsequence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongestIncreasingSubSequenceTest {

    LongestIncreasingSubSequence lis = new LongestIncreasingSubSequence();

    @Test
    void longestIncreasingSubsequence() {
        String source = "abhkdfm";
        int longestIncreasingSubsequence = lis.longestIncreasingSubsequence(source);
//        assertThat(longestIncreasingSubsequence).isEqualTo("abhkm");
        assertThat(longestIncreasingSubsequence).isEqualTo(5);
    }
}