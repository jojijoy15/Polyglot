package com.problems.learning.algo.simplyrecursive;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RemoveDuplicatesTest {

    RemoveDuplicates removeDuplicates = new RemoveDuplicates();

    @Test
    void removeDuplicatesNoConsecutiveDuplicates() {
        String input = "abcde";
        String uniqueOnlyString = removeDuplicates.removeConsecutiveDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo(input);
    }

    @Test
    void removeDuplicatesConsecutiveConsecutiveDuplicates() {
        String input = "abbcdde";
        String uniqueOnlyString = removeDuplicates.removeConsecutiveDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo("ace");
    }

    @Test
    void removeDuplicatesConsecutiveConsecutiveDuplicatesOnly() {
        String input = "abbbcddeee";
        String uniqueOnlyString = removeDuplicates.removeConsecutiveDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo("abce");
    }

    @Test
    void removeConsecutiveDuplicatesConsecutiveDuplicateInterlaced() {
        String input = "aaabbbaccddeee";
        String uniqueOnlyString = removeDuplicates.removeConsecutiveDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo("abae");
    }

    @Test
    void removeConsecutiveDuplicatesConsecutiveDuplicateInBetween() {
        String input = "aaabbbbaccddeee";
        String uniqueOnlyString = removeDuplicates.removeConsecutiveDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo("e");
    }

    @Test
    void removeDuplicatesNoConsecutiveDuplicatesInPlace() {
        String input = "abcde";
        String uniqueOnlyString = removeDuplicates.removeDuplicatesInPlace(input);
        assertThat(uniqueOnlyString).isEqualTo(input);
    }

    @Test
    void removeDuplicatesConsecutiveConsecutiveDuplicatesInPlace() {
        String input = "abbcdde";
        String uniqueOnlyString = removeDuplicates.removeDuplicatesInPlace(input);
        assertThat(uniqueOnlyString).isEqualTo("ace");
    }

    @Test
    void removeDuplicatesConsecutiveConsecutiveDuplicatesOnlyInPlace() {
        String input = "abbbcddeee";
        String uniqueOnlyString = removeDuplicates.removeDuplicatesInPlace(input);
        assertThat(uniqueOnlyString).isEqualTo("abce");
    }

    @Test
    void removeConsecutiveDuplicatesConsecutiveDuplicateInterlacedInPlace() {
        String input = "aaabbbaccddeee";
        String uniqueOnlyString = removeDuplicates.removeDuplicatesInPlace(input);
        assertThat(uniqueOnlyString).isEqualTo("abae");
    }

    @Test
    void removeConsecutiveDuplicatesConsecutiveDuplicateInBetweenInPlace() {
        String input = "aaabbbbaccddeee";
        String uniqueOnlyString = removeDuplicates.removeDuplicatesInPlace(input);
        assertThat(uniqueOnlyString).isEqualTo("e");
    }
}