package com.problems.learning.algo.backtracking.permutation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PermutationsTest {

    Permutations permutations = new Permutations();

    @Test
    void permutation() {
        int[] elements = {1, 2, 3};
        int[][] permutation = permutations.permutation(elements);
        assertThat(permutation).isDeepEqualTo(
            new int[][]{
                {1, 2, 3}, {2, 1, 3},
                {2, 3, 1},{1, 3, 2},
                {3, 1, 2}, {3, 2, 1},
            });
    }

    @Test
    void permutationIteratively() {
        int[] elements = {1, 2, 3};
        int[][] permutation = permutations.permutationIteratively(elements);
        assertThat(permutation).isDeepEqualTo(
            new int[][]{
                    {3, 2, 1}, {2, 3, 1},
                    {2, 1, 3}, {3, 1, 2},
                    {1, 3, 2}, {1, 2, 3}
            });
    }
}