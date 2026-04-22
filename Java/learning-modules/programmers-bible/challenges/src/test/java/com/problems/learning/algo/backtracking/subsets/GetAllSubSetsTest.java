package com.problems.learning.algo.backtracking.subsets;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GetAllSubSetsTest {

    private GetAllSubSets getAllSubSets = new GetAllSubSets();

    @Test
    void subSets() {
        int[] elements = new int[]{1, 2, 2, 3};
        Integer[][] subSets = getAllSubSets.subSets(elements);
        int[][] answer = {{}, {1}, {2}, {3}, {1, 2}, {2, 3}, {1, 3}, {1, 2, 3}};

        List<List<Integer>> actual = Arrays.stream(subSets)
                .map(row -> Arrays.stream(row).toList())
                .toList();

        List<List<Integer>> expected = Arrays.stream(answer)
                .map(row -> Arrays.stream(row).boxed().toList())
                .toList();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

}