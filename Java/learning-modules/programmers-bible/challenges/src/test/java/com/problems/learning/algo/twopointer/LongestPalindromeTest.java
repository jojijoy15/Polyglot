package com.problems.learning.algo.twopointer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongestPalindromeTest {

    LongestPalindrome lp = new LongestPalindrome();

    @Test
    void getLongestPalindrome() {
        String input = "abcabcbb";
        int[] lpd = lp.getLongestPalindrome(input);
        assertThat(lpd).containsExactly(4, 3);
        assertThat(input.substring(lpd[0], lpd[0] + lpd[1]))
            .isEqualTo("bcb");

    }

}