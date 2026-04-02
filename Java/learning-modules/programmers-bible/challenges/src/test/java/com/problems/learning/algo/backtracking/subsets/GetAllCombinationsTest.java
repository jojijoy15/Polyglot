package com.problems.learning.algo.backtracking.subsets;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GetAllCombinationsTest {


    private GetAllCombinations getAllCombinations = new GetAllCombinations();

    @Test
    void combinations() {
        int[] elements = new int[]{7, 21, 3, 4, 5};
        int k = 2;

        Integer[][] combinations = getAllCombinations.combinations(elements, k);

        int[][] answer = {
                {7, 21}, {21, 3}, {3, 4},
                {4, 5}, {7, 5}, {21, 5},
                {3, 5}, {7, 3}, {7,4}, {21, 4}
        };

        List<List<Integer>> actual = Arrays.stream(combinations)
                .map(row -> Arrays.stream(row).toList())
                .toList();

        List<List<Integer>> expected = Arrays.stream(answer)
                .map(row -> Arrays.stream(row).boxed().toList())
                .toList();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

}