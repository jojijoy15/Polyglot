package com.problems.learning.algo.dp.subsequence;

import com.problems.learning.algo.dp.subsequence.LongestCommonSubSequence;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LongestCommonSubSequenceTest {

    LongestCommonSubSequence longestCommonSubSequence =  new LongestCommonSubSequence();

    @Test
    void longestCommonSubsequence() {
        String first = "abcde";
        String second = "ace";
        long result = longestCommonSubSequence.longestCommonSubsequence(first, second);
        assertThat(result).isEqualTo(3);
    }

    @Test
    void longestCommonSubsequenceWithIndex() {
        String first = "abcde";
        String second = "ace";
        Map.Entry<Integer, List<Integer>> result = longestCommonSubSequence.longestCommonSubsequenceWithIndex(first, second);
        assertThat(result).isEqualTo(Map.entry(3,List.of(0, 2, 4)));
    }
}