package com.problems.learning.algo.hard.matchconditions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxMatchingConditionTest {

    MaxMatchingCondition maxConditionMatch = new MaxMatchingCondition();

    @Test
    void maxMatches() {
        int[] elements = {4, 6, 5, 1};
        //1 4 5 6
        int maxConditionMatches = maxConditionMatch.maxMatches(elements);
        assertEquals(3, maxConditionMatches);
    }

    @Test
    void maxMatchesDuplicates() {
        int[] elements = {1, 1, 2, 2};
        //1 2 1 2
        int maxConditionMatches = maxConditionMatch.maxMatches(elements);
        assertEquals(2, maxConditionMatches);
    }
}