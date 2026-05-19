package com.problems.learning.algo.search;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PivotElementTest {

    private final PivotElement solver = new PivotElement();

    @Test
    void purelyAscending_noPivot() {
        assertThat(solver.findPivot(new int[]{1, 2, 3, 4, 5})).isEqualTo(-1);
    }

    @Test
    void purelyDescending_noPivot() {
        assertThat(solver.findPivot(new int[]{5, 4, 3, 2, 1})).isEqualTo(-1);
    }

    @Test
    void valley_descendThenAscend() {
        // [5,4,1,2,3] → before 1: [5,4] desc, after 1: [2,3] asc → pivot = 1
        assertThat(solver.findPivot(new int[]{5, 4, 1, 2, 3})).isEqualTo(1);
    }

    @Test
    void peak_ascendThenDescend() {
        // [1,2,5,4,3] → before 5: [1,2] asc, after 5: [4,3] desc → pivot = 5
        assertThat(solver.findPivot(new int[]{1, 2, 5, 4, 3})).isEqualTo(5);
    }

    @Test
    void threeElements_peak() {
        assertThat(solver.findPivot(new int[]{1, 3, 2})).isEqualTo(3);
    }

    @Test
    void threeElements_valley() {
        assertThat(solver.findPivot(new int[]{3, 1, 2})).isEqualTo(1);
    }

    @Test
    void twoElements_noPivot() {
        assertThat(solver.findPivot(new int[]{1, 2})).isEqualTo(-1);
    }

    @Test
    void nullArray_noPivot() {
        assertThat(solver.findPivot(null)).isEqualTo(-1);
    }

    @Test
    void notStrictlyOrdered_noPivot() {
        // [1, 2, 5, 5, 3] — not strictly ascending before pivot
        assertThat(solver.findPivot(new int[]{1, 2, 5, 5, 3})).isEqualTo(-1);
    }

    @Test
    void irregularShape_noPivot() {
        // [1, 3, 2, 4] — ascending, then descending, then ascending again → no single pivot
        assertThat(solver.findPivot(new int[]{1, 3, 2, 4})).isEqualTo(-1);
    }
}

