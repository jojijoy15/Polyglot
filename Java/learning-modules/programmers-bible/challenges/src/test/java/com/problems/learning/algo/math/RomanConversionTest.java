package com.problems.learning.algo.math;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RomanConversionTest {

    RomanConversion romanConversion = new RomanConversion();

    @Test
    void romanConversionTest() {
        int input = 1001;
        String roman = romanConversion.toRoman(input);
        assertThat(roman).isEqualTo("MI");
    }

    @Test
    void romanConversionTestComplex() {
        int input = 3749;
        String roman = romanConversion.toRoman(input);
        assertThat(roman).isEqualTo("MMMDCCXLIX");
    }

    @Test
    void romanConversionTestYear() {
        int input = 1994;
        String roman = romanConversion.toRoman(input);
        assertThat(roman).isEqualTo("MCMXCIV");
    }

    @Test
    void toIntTest() {
        String input = "MI";
        int roman = romanConversion.toInt(input);
        assertThat(roman).isEqualTo(1001);
    }

    @Test
    void toIntTestComplex() {
        String input = "MMMDCCXLIX" ;
        int roman = romanConversion.toInt(input);
        assertThat(roman).isEqualTo(3749);
    }

    @Test
    void toIntTestYear() {
        String input = "MCMXCIV";
        int roman = romanConversion.toInt(input);
        assertThat(roman).isEqualTo(1994);
    }

}