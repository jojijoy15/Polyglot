package com.problems.learning.algo.advanced;

import com.problems.learning.algo.advanced.string.matching.KMPStringMatching;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KMPStringMatchingTest {

    KMPStringMatching kmpStringMatching = new KMPStringMatching();

    @Test
    void testKMPSimple() {
        String first = "anagram";
        String second = "gram";
        List<Integer> indexes = kmpStringMatching.strStr(first, second);
        assertThat(indexes).containsExactly(3);
    }


    @Test
    void testKMP() {
        String first = "anagramamagram";
        String second = "gram";
        List<Integer> indexes = kmpStringMatching.strStr(first, second);
        assertThat(indexes).containsExactly(3, 10);
    }


}