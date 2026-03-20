package com.problems.learning.algo.dp.subsequence;

import com.problems.learning.algo.dp.subsequence.LongestIncreasingSubSequence;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongestIncreasingSubSequenceTest {

    LongestIncreasingSubSequence lis = new LongestIncreasingSubSequence();

    @Test
    void longestIncreasingSubsequence() {
        String source = "abhckdfm";
        int longestIncreasingSubsequence = lis.longestIncreasingSubsequence(source);
//        assertThat(longestIncreasingSubsequence).isEqualTo("abhkm");
        assertThat(longestIncreasingSubsequence).isEqualTo(5);
    }
}