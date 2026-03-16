package com.problems.learning.algo.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RemoveDuplicatesTest {

    RemoveDuplicates removeDuplicates = new RemoveDuplicates();

    @Test
    void removeDuplicatesNoDuplicates() {
        String input = "abcde";
        String uniqueOnlyString = removeDuplicates.removeDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo(input);
    }

    @Test
    void removeDuplicatesConsecutiveDuplicates() {
        String input = "abbcdde";
        String uniqueOnlyString = removeDuplicates.removeDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo("ace");
    }

    @Test
    void removeDuplicatesConsecutiveDuplicatesOnly() {
        String input = "abbbcddeee";
        String uniqueOnlyString = removeDuplicates.removeDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo("abce");
    }

    @Test
    void removeDuplicatesConsecutiveDuplicateInterlaced() {
        String input = "aaabbbaccddeee";
        String uniqueOnlyString = removeDuplicates.removeDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo("abae");
    }

    @Test
    void removeDuplicatesConsecutiveDuplicateInBetween() {
        String input = "aaabbbbaccddeee";
        String uniqueOnlyString = removeDuplicates.removeDuplicates(input);
        assertThat(uniqueOnlyString).isEqualTo("e");
    }
}