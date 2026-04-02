package com.problems.learning.algo.hard.merge;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MergeByGapTest {

    MergeByGap mergeByGap = new MergeByGap();

    @Test
    void merge() {
        int[] first = new int[]{11, 12, 100, 125, 700};
        int[] second = new int[]{10, 15, 70};
        mergeByGap.merge(first, second);
        assertThat(first).containsExactly(10, 11, 12, 15, 70);
        assertThat(second).containsExactly(100, 125, 700);
    }

    @Test
    void mergeFullFlip() {
        int[] first = new int[]{100, 125, 700};
        int[] second = new int[]{10, 15, 70};
        mergeByGap.merge(first, second);
        assertThat(first).containsExactly(10, 15, 70);
        assertThat(second).containsExactly(100, 125, 700);
    }

    @Test
    void mergeWithDup() {
        int[] first = new int[]{10, 125, 700};
        int[] second = new int[]{10, 15, 70};
        mergeByGap.merge(first, second);
        assertThat(first).containsExactly(10, 10, 15);
        assertThat(second).containsExactly(70, 125, 700);
    }

    @Test
    void mergeWithSecondListBigger() {
        int[] first = new int[]{10, 125, 700};
        int[] second = new int[]{10, 15, 70, 120, 130};
        mergeByGap.merge(first, second);
        assertThat(first).containsExactly(10, 10, 15);
        assertThat(second).containsExactly(70, 120, 125, 130, 700);
    }


}