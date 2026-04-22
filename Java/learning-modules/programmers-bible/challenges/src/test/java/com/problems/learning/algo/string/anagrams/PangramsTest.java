package com.problems.learning.algo.string.anagrams;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PangramsTest {

    Pangrams pangrams = new Pangrams();

    @Test
    void findMissingAlphabetAllPresent() {
        String text = "A quick brown fox jumps over the lazy dog";
        String missingAlphabets = pangrams.findMissingAlphabet(text);
        assertThat(missingAlphabets).isEmpty();
    }

    @Test
    void findMissingAlphabetSomeMissing() {
        String text = "The quick brown fox jumps";
        String missingAlphabets = pangrams.findMissingAlphabet(text);
        assertThat(missingAlphabets).isEqualTo("adglvyz");
    }
}