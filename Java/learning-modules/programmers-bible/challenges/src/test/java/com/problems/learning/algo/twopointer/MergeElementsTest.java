package com.problems.learning.algo.twopointer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MergeElementsTest {

    MergeElements mergeElements = new MergeElements();

    @Test
    void mergeSorted() {
        int[] firstSetOfElements = new int[]{2, 5, 8, 10, 15};
        int[] secondSetOfElements = new int[]{1, 4, 7, 9, 13};
        int[] actual = mergeElements.mergeSorted(firstSetOfElements, secondSetOfElements);
        assertThat(actual).containsExactly(1, 2, 4, 5, 7, 8, 9, 10, 13, 15);
    }

    @Test
    void mergeSortedContained() {
        int[] firstSetOfElements = new int[]{1, 2, 4, 0, 0, 0};
        int[] secondSetOfElements = new int[]{3, 5, 6};
        int[] actual = mergeElements.mergeSortedContained(firstSetOfElements, 2, secondSetOfElements);
        assertThat(actual).containsExactly(1, 2, 3, 4, 5, 6);
    }
}