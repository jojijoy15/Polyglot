package com.problems.learning.algo.dp.lc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongestCommonSubStringTest {

    LongestCommonSubString solver = new LongestCommonSubString();

    @Test
    void basicExample() {
        // "abcde" and "abfce" → common substring "ab" (length 2)
        assertThat(solver.longestCommonSubstring("abcde", "abfce")).isEqualTo(2);
    }

    @Test
    void basicExampleValue() {
        assertThat(solver.longestCommonSubstringValue("abcde", "abfce")).isEqualTo("ab");
    }

    @Test
    void identicalStrings() {
        assertThat(solver.longestCommonSubstring("abcde", "abcde")).isEqualTo(5);
        assertThat(solver.longestCommonSubstringValue("abcde", "abcde")).isEqualTo("abcde");
    }

    @Test
    void noCommonSubstring() {
        assertThat(solver.longestCommonSubstring("abc", "xyz")).isEqualTo(0);
        assertThat(solver.longestCommonSubstringValue("abc", "xyz")).isEmpty();
    }

    @Test
    void commonSubstringInMiddle() {
        // "xabcy" and "dabcz" → "abc" (length 3)
        assertThat(solver.longestCommonSubstring("ABCDGH", "ACDGHR")).isEqualTo(4);
        assertThat(solver.longestCommonSubstringValue("ABCDGH", "ACDGHR")).isEqualTo("CDGH");
    }

    @Test
    void commonSubstringAtEnd() {
        // "helloworld" and "myworld" → "world" (length 5)
        assertThat(solver.longestCommonSubstring("helloworld", "myworld")).isEqualTo(5);
        assertThat(solver.longestCommonSubstringValue("helloworld", "myworld")).isEqualTo("world");
    }

    @Test
    void singleCharacterMatch() {
        assertThat(solver.longestCommonSubstring("a", "a")).isEqualTo(1);
        assertThat(solver.longestCommonSubstringValue("a", "a")).isEqualTo("a");
    }

    @Test
    void singleCharacterNoMatch() {
        assertThat(solver.longestCommonSubstring("a", "b")).isEqualTo(0);
    }

    @Test
    void emptyString() {
        assertThat(solver.longestCommonSubstring("", "abc")).isEqualTo(0);
        assertThat(solver.longestCommonSubstring("abc", "")).isEqualTo(0);
    }

    @Test
    void multipleCommonSubstringsPicksLongest() {
        // "abcxyz" and "xyzabc" → "abc" and "xyz" both length 3
        assertThat(solver.longestCommonSubstring("abcxyz", "xyzabc")).isEqualTo(3);
    }

    @Test
    void subsequenceVsSubstring() {
        // "abcde" and "ace" → subsequence = 3 ("ace"), but substring = 1 ("a" or "c" or "e")
        assertThat(solver.longestCommonSubstring("abcde", "ace")).isEqualTo(1);
    }
}

