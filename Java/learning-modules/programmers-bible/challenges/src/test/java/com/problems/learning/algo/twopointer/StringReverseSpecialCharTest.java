package com.problems.learning.algo.twopointer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringReverseSpecialCharTest {

    private StringReverseSpecialChar stringAlgorithms = new StringReverseSpecialChar();

    @Test
    void reverseWithSpecialCharIntactWithNoSpecialChar() {
        String input = "Hello";
        String actual = stringAlgorithms.reverseWithSpecialCharIntact(input);
        assertThat(actual).isEqualTo("olleH");
    }

    @Test
    void reverseWithSpecialCharIntactWithSpecialChar() {
        String input = "He#llo";
        String actual = stringAlgorithms.reverseWithSpecialCharIntact(input);
        assertThat(actual).isEqualTo("ol#leH");
    }

    @Test
    void reverseWithSpecialCharIntactEmptyString() {
        String input = "";
        String actual = stringAlgorithms.reverseWithSpecialCharIntact(input);
        assertThat(actual).isEqualTo("");
    }

    @Test
    void reverseWithSpecialCharIntactOnlySpecialChar() {
        String input = "#$@";
        String actual = stringAlgorithms.reverseWithSpecialCharIntact(input);
        assertThat(actual).isEqualTo("#$@");
    }

    @Test
    void reverseWithSpecialCharIntactIntermediateSpecialChar() {
        String input = "H#e$l@l!0";
        String actual = stringAlgorithms.reverseWithSpecialCharIntact(input);
        assertThat(actual).isEqualTo("0#l$l@e!H");
    }



    @Test
    void reverseWithSpecialCharIntactConsecutiveSpecialChar2() {
        String input = "/gH?yZx";
        String actual = stringAlgorithms.reverseWithSpecialCharIntact(input);
        assertThat(actual).isEqualTo("/xZ?yHg");
    }
}