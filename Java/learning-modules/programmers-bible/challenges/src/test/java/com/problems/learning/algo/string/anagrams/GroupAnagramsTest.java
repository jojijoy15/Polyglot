package com.problems.learning.algo.string.anagrams;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GroupAnagramsTest {

    private final GroupAnagrams groupAnagrams = new GroupAnagrams();

    @Test
    void groupAnagramsFreq() {
        String[] words = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> lists = groupAnagrams.groupAnagramsFreq(words);
        assertThat(lists).contains(
                List.of("eat", "tea", "ate"),
                List.of("tan", "nat"),
                List.of("bat")
        );

    }
}