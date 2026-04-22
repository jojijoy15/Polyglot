package com.problems.learning.algo.twopointer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RemoveToSortTest {

    RemoveToSort removeToSort = new RemoveToSort();

    @Test
    void removeToSort() {
        int[] values = {1, 2, 3, 10, 4, 2, 3, 5};
        int result = removeToSort.findLengthOfShortestSubarray(values);
        assertThat(result).isEqualTo(3);
    }

    @Test
    void removeToSortNested() {
        int[] values = {1, 2, 3, 10, 3, 2, 4, 2, 3, 5};
        int result = removeToSort.findLengthOfShortestSubarray(values);
        assertThat(result).isEqualTo(5);
    }

    @Test
    void removeToSortAlreadySorted() {
        int[] values = {2, 3, 4, 5, 7};
        int result = removeToSort.findLengthOfShortestSubarray(values);

        assertThat(result).isEqualTo(0);

    }
}