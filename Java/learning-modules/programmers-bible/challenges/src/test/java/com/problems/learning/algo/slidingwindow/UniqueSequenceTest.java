package com.problems.learning.algo.slidingwindow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UniqueSequenceTest {

    UniqueSequence uniqueSequence = new UniqueSequence();

    @Test
    void longestLongestUniqueStringLengthValidCaseOddLength() {
        String input = "abcdabb";
        int maxLength = uniqueSequence.longestUniqueStringLength(input);
        assertThat(maxLength).isEqualTo(4);
    }

    @Test
    void longestLongestUniqueStringLengthWhenEmpty() {
        String input = "";
        int maxLength = uniqueSequence.longestUniqueStringLength(input);
        assertThat(maxLength).isEqualTo(0);
    }

    @Test
    void longestLongestUniqueStringLengthValidCaseEvenLength() {
        String input = "abcdabbefhgf";
        int maxLength = uniqueSequence.longestUniqueStringLength(input);
        assertThat(maxLength).isEqualTo(5);
    }

    @Test
    void longestUniqueStringValidCaseOddLength() {
        String input = "abcdabb";
        String uniqueString =uniqueSequence.longestUniqueString(input);
        assertThat(uniqueString).isEqualTo("abcd");
    }

    @Test
    void longestUniqueStringWhenEmpty() {
        String input = "";
        String uniqueString = uniqueSequence.longestUniqueString(input);
        assertThat(uniqueString).isEqualTo("");
    }

    @Test
    void longestUniqueStringValidCaseEvenLength() {
        String input = "abcdabbefhgf";
        String uniqueString = uniqueSequence.longestUniqueString(input);
        assertThat(uniqueString).isEqualTo("befhg");
    }

    @Test
    void longestUniqueStringKDistinctValidCaseOddLength() {
        String input = "eceba";
        String uniqueString = uniqueSequence.longestSubstringAtMostKDistinct(input, 2);
        assertThat(uniqueString).isEqualTo("ece");
    }

    @Test
    void longestUniqueStringKDistinctValidCaseEvenLength() {
        String input = "abcadcacacacac";
        String uniqueString = uniqueSequence.longestSubstringAtMostKDistinct(input, 3);
        assertThat(uniqueString).isEqualTo("cadcacacacac");
    }
}