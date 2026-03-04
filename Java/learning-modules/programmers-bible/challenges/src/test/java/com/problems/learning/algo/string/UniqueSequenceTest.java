package com.problems.learning.algo.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UniqueSequenceTest {

    UniqueSequence uniqueSequence = new UniqueSequence();

    @Test
    void uniqueStringLengthValidCaseOddLength() {
        String input = "abcdabb";
        int maxLength = uniqueSequence.uniqueStringLength(input);
        assertThat(maxLength).isEqualTo(4);
    }

    @Test
    void uniqueStringLengthWhenEmpty() {
        String input = "";
        int maxLength = uniqueSequence.uniqueStringLength(input);
        assertThat(maxLength).isEqualTo(0);
    }

    @Test
    void uniqueStringLengthValidCaseEvenLength() {
        String input = "abcdabbefhgf";
        int maxLength = uniqueSequence.uniqueStringLength(input);
        assertThat(maxLength).isEqualTo(5);
    }

    @Test
    void uniqueStringValidCaseOddLength() {
        String input = "abcdabb";
        String uniqueString =uniqueSequence.uniqueString(input);
        assertThat(uniqueString).isEqualTo("abcd");
    }

    @Test
    void uniqueStringWhenEmpty() {
        String input = "";
        String uniqueString = uniqueSequence.uniqueString(input);
        assertThat(uniqueString).isEqualTo("");
    }

    @Test
    void uniqueStringValidCaseEvenLength() {
        String input = "abcdabbefhgf";
        String uniqueString = uniqueSequence.uniqueString(input);
        assertThat(uniqueString).isEqualTo("befhg");
    }
}