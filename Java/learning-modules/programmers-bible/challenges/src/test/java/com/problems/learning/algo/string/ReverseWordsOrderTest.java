package com.problems.learning.algo.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReverseWordsOrderTest {


    ReverseWordsOrder reverseWords = new ReverseWordsOrder();

    @Test
    void basicSentence() {
        assertThat(reverseWords.reverseWords("this is     a cat")).isEqualTo("cat a is this");
    }

    @Test
    void leadingAndTrailingSpaces() {
        assertThat(reverseWords.reverseWords(" hello world ")).isEqualTo("world hello");
    }

    @Test
    void multipleSpacesBetweenWords() {
        assertThat(reverseWords.reverseWords("a   good   example")).isEqualTo("example good a");
    }

    @Test
    void singleWord() {
        assertThat(reverseWords.reverseWords("hello")).isEqualTo("hello");
    }

    @Test
    void emptyString() {
        assertThat(reverseWords.reverseWords("")).isEqualTo("");
    }

    @Test
    void nullString() {
        assertThat(reverseWords.reverseWords(null)).isEqualTo("");
    }

    @Test
    void onlySpaces() {
        assertThat(reverseWords.reverseWords("    ")).isEqualTo("");
    }

    @Test
    void twoWords() {
        assertThat(reverseWords.reverseWords("hello world")).isEqualTo("world hello");
    }
}
