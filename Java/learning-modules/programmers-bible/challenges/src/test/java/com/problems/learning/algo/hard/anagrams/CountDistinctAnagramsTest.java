package com.problems.learning.algo.hard.anagrams;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountDistinctAnagramsTest {

    CountDistinctAnagrams solver = new CountDistinctAnagrams();

    @Test
    void allUniqueCharacters() {
        // "abc def" → 3! * 3! = 6 * 6 = 36
        assertThat(solver.countAnagrams("abc def")).isEqualTo(36);
    }

    @Test
    void singleWord() {
        // "abc" → 3! = 6
        assertThat(solver.countAnagrams("abc")).isEqualTo(6);
    }

    @Test
    void wordWithRepeatingChars() {
        // "aa" → 2!/2! = 1
        assertThat(solver.countAnagrams("aa")).isEqualTo(1);
    }

    @Test
    void leetcodeExample() {
        // "too hot" → "too" = 3!/2! = 3, "hot" = 3! = 6 → 3 * 6 = 18
        assertThat(solver.countAnagrams("too hot")).isEqualTo(18);
    }

    @Test
    void singleCharacter() {
        // "a" → 1! = 1
        assertThat(solver.countAnagrams("a")).isEqualTo(1);
    }

    @Test
    void allSameCharacters() {
        // "aaa bbb" → (3!/3!) * (3!/3!) = 1 * 1 = 1
        assertThat(solver.countAnagrams("aaa bbb")).isEqualTo(1);
    }

    @Test
    void mixedRepetitions() {
        // "aab" → 3!/2! = 3, "cd" → 2! = 2 → 3 * 2 = 6
        assertThat(solver.countAnagrams("aab cd")).isEqualTo(6);
    }

    @Test
    void singleCharWords() {
        // "a b c" → 1 * 1 * 1 = 1
        assertThat(solver.countAnagrams("a b c")).isEqualTo(1);
    }

    @Test
    void longRepeatingWord() {
        // "aaabb" → 5!/(3!*2!) = 120/12 = 10
        assertThat(solver.countAnagrams("aaabb")).isEqualTo(10);
    }
}

