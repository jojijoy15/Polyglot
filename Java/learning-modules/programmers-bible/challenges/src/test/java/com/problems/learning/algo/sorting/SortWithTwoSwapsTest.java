package com.problems.learning.algo.sorting;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class SortWithTwoSwapsTest {
    SortWithTwoSwaps solver = new SortWithTwoSwaps();
    @Test
    void threeCycle_twoSwaps() {
        // [2,4,3,1] ? swap(2,1) ? [1,4,3,2] ? swap(4,2) ? [1,2,3,4]
        assertThat(solver.canSortInTwoSwaps(new int[]{2, 4, 3, 1})).isTrue();
    }
    @Test
    void twoCycle_oneSwapNeeded() {
        // [1,3,2,4] ? only needs 1 swap ? false
        assertThat(solver.canSortInTwoSwaps(new int[]{1, 3, 2, 4})).isFalse();
    }
    @Test
    void fiveCycle_moreThanTwoSwaps() {
        // [2,4,6,9,1] ? needs 4 swaps ? false
        assertThat(solver.canSortInTwoSwaps(new int[]{2, 4, 6, 9, 1})).isFalse();
    }
    @Test
    void twoIndependent2Cycles() {
        // [2,1,4,3] ? swap(2,1) + swap(4,3) ? [1,2,3,4]
        assertThat(solver.canSortInTwoSwaps(new int[]{2, 1, 4, 3})).isTrue();
    }
    @Test
    void fourCycle_needsThreeSwaps() {
        // [2,3,4,1] ? 4-cycle ? needs 3 swaps ? false
        assertThat(solver.canSortInTwoSwaps(new int[]{2, 3, 4, 1})).isFalse();
    }
    @Test
    void alreadySorted() {
        // [1,2,3,4] ? 0 swaps needed ? false
        assertThat(solver.canSortInTwoSwaps(new int[]{1, 2, 3, 4})).isFalse();
    }
    @Test
    void singleElement() {
        assertThat(solver.canSortInTwoSwaps(new int[]{5})).isFalse();
    }
    @Test
    void twoElements() {
        // [2,1] ? 1 swap needed ? false
        assertThat(solver.canSortInTwoSwaps(new int[]{2, 1})).isFalse();
    }
    @Test
    void threeCycleWithDuplicates() {
        // [3,1,1,2] sorted=[1,1,2,3] ? 3 diffs ? 2 swaps
        assertThat(solver.canSortInTwoSwaps(new int[]{3, 1, 1, 2})).isTrue();
    }
    @Test
    void fourDiffsWithDuplicates() {
        // [2,2,1,1] sorted=[1,1,2,2] ? two 2-cycles ? 2 swaps
        assertThat(solver.canSortInTwoSwaps(new int[]{2, 2, 1, 1})).isTrue();
    }
    @Test
    void reverseThreeElements() {
        // [3,2,1] ? swap(3,1) ? [1,2,3] ? only 1 swap ? false
        assertThat(solver.canSortInTwoSwaps(new int[]{3, 2, 1})).isFalse();
    }
    @Test
    void largeMismatches() {
        // [5,4,3,2,1] → index 2 already correct → 4 diffs → two 2-cycles
        // swap(5,1) → [1,4,3,2,5] → swap(4,2) → [1,2,3,4,5]
        assertThat(solver.canSortInTwoSwaps(new int[]{5, 4, 3, 2, 1})).isTrue();
    }
}
