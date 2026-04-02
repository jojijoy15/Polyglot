package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DelimitedStringSumTest {

    DelimitedStringSum dls = new DelimitedStringSum();

    @Test
    void sum() {
        String input = "AB_12_CD_23_EF_20";
        int sum = dls.sum(input);
        assertThat(sum).isEqualTo(55);
    }

    @Test
    void sumUnderScoreBeginnnig() {
        String input = "_12_CD_23_EF_20";
        int sum = dls.sum(input);
        assertThat(sum).isEqualTo(55);
    }

    @Test
    void sumUnderScoreEnd() {
        String input = "12_CD_23_EF_20_";
        int sum = dls.sum(input);
        assertThat(sum).isEqualTo(55);
    }

    @Test
    void sumUnderScoreSingleDigits() {
        String input = "12_CD_2_E_F_20";
        int sum = dls.sum(input);
        assertThat(sum).isEqualTo(34);
    }

    @Test
    void sumSingleDigit() {
        String input = "3";
        int sum = dls.sum(input);
        assertThat(sum).isEqualTo(3);
    }

    @Test
    void sumSingleCharacter() {
        String input = "A";
        int sum = dls.sum(input);
        assertThat(sum).isEqualTo(0);
    }

    @Test
    void sumSingleUnderscore() {
        String input = "_";
        int sum = dls.sum(input);
        assertThat(sum).isEqualTo(0);
    }
}