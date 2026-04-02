package com.problems.learning.algo.string;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LongestRepeatingSubStringTest {

    LongestRepeatingSubString longestRepeatingSubString = new LongestRepeatingSubString();

    @Test
    void findLongestRepeatingCharacter() {
        String input = "aabbbc";
        Map.Entry<Integer, Integer> longRepeatingSeq = longestRepeatingSubString
                .findLongestRepeatingCharacter(input);
        int start = longRepeatingSeq.getKey();
        int length = longRepeatingSeq.getValue();
        String longestRepeatingPattern = input.substring(start, start + length);
        assertThat(longestRepeatingPattern).isEqualTo("bbb");
    }

    @Test
    void findLongestRepeatingCharacterSame() {
        String input = "aabbbccccd";
        Map.Entry<Integer, Integer> longRepeatingSeq = longestRepeatingSubString
                .findLongestRepeatingCharacter(input);
        int start = longRepeatingSeq.getKey();
        int length = longRepeatingSeq.getValue();
        String longestRepeatingPattern = input.substring(start, start + length);
        assertThat(longestRepeatingPattern).isEqualTo("cccc");
    }
}