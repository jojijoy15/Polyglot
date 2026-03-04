package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringToIntegerSpecialHandlingsTest {

    StringToIntegerSpecialHandlings testObject = new StringToIntegerSpecialHandlings();

    @Test
    void asciiToIntInitialCharacters() {
        String input = "ASD7";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(-100);
    }

    @Test
    void asciiToIntInitialSpaceCharacters() {
        String input = "    ASD7";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(-100);
    }

    @Test
    void asciiToIntInSpaceInBetweenCharacters() {
        String input = "7   343";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(7);
    }

    @Test
    void asciiToIntSignInBetweenCharacters() {
        String input = "75+   343";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(75);
    }

    @Test
    void asciiToIntBreachLimitPos() {
        String input = "23000003240234325";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    void asciiToIntBreachLimitNeg() {
        String input = "-23000003240234325";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    void asciiToIntValidCase() {
        String input = "7";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(7);

    }

    @Test
    void asciiToIntValidPosSignCase() {
        String input = "+7";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(7);
    }

    @Test
    void asciiToIntValidNegSignCase() {
        String input = "-7";
        Integer expected = testObject.asciiToInt(input);
        assertThat(expected).isEqualTo(-7);
    }
}

