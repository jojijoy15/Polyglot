package com.problems.learning.algo.string;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExtractTuplesTest {

    ExtractTuples extractTuples = new ExtractTuples();

    @Test
    void getTuples() {
        String word = "abbccde";
        int size = 2;
        List<String> tuples = extractTuples.getTuples(word, size);
        assertThat(tuples)
            .containsExactlyInAnyOrder("ab", "bb", "bc", "cc", "cd", "de");
    }

    @Test
    void getTuplesWithOutDuplicates() {
        String word = "abbbcccde";
        int size = 2;
        List<String> tuples = extractTuples.getTuples(word, size);
        assertThat(tuples)
                .containsExactlyInAnyOrder("ab", "bb", "bc", "cc", "cd", "de");
    }
}