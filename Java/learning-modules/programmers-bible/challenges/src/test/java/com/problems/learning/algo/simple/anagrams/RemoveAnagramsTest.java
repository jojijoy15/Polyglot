package com.problems.learning.algo.simple.anagrams;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RemoveAnagramsTest {

    RemoveAnagrams removeAnagrams = new RemoveAnagrams();

    @Test
    void removeAnagrams() {
        String[] words = {"code", "doce", "ecod", "framer", "frame"};
        String[] nonAnagramWords = removeAnagrams.removeAnagrams(words);
        assertThat(nonAnagramWords).containsExactly("code", "frame", "framer");
    }
}