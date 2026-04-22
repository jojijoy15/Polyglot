package com.problems.learning.algo.greedy;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartitionLabelsTest {

    private final PartitionLabels partitionLabels = new PartitionLabels();

    @Test
    void shouldPartitionGivenExample() {
        String input = "ababbacaadefgegdehjikjlhjkl";
        List<String> expected = List.of("ababbacaa", "defgegde","hjikjlhjkl");

        assertEquals(expected, partitionLabels.partition(input));
    }

    @Test
    void shouldReturnWholeStringWhenNoSplitPossible() {
        String input = "abcabc";
        List<String> expected = List.of("abcabc");

        assertEquals(expected, partitionLabels.partition(input));
    }

    @Test
    void shouldReturnEachCharWhenAllUnique() {
        String input = "abcdef";
        List<String> expected = List.of("a", "b", "c", "d", "e", "f");

        assertEquals(expected, partitionLabels.partition(input));
    }

    @Test
    void shouldReturnTwoSplitWhenOneCharBreaksPattern() {
        String input = "abcdabc";
        List<String> expected = List.of("abc", "d", "abc");

        assertEquals(expected, partitionLabels.partition(input));
    }

    @Test
    void shouldHandleSingleCharacter() {
        String input = "a";
        List<String> expected = List.of("a");

        assertEquals(expected, partitionLabels.partition(input));
    }

    @Test
    void shouldHandleEmptyString() {
        assertEquals(List.of(), partitionLabels.partition(""));
    }

    @Test
    void shouldHandleRepeatedSingleChar() {
        String input = "aaaa";
        List<String> expected = List.of("aaaa");

        assertEquals(expected, partitionLabels.partition(input));
    }
}

