package com.problems.learning.algo.twopointer;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
class TwoSumTest {
    TwoSum twoSum = new TwoSum();
    @Test
    void twoSumIndicesBasic() {
        int[] result = twoSum.twoSumIndices(new int[]{2, 7, 11, 15}, 9);
        assertThat(result).containsExactly(0, 1);
    }
    @Test
    void twoSumIndicesNoMatch() {
        int[] result = twoSum.twoSumIndices(new int[]{1, 2, 3}, 10);
        assertThat(result).isEmpty();
    }
    @Test
    void allPairsBasic() {
        List<int[]> result = twoSum.twoSumAllPairs(new int[]{1, 2, 3, 4, 5, 6}, 7);
        assertThat(result).hasSize(3);
        assertThat(result.get(0)).containsExactly(1, 6);
        assertThat(result.get(1)).containsExactly(2, 5);
        assertThat(result.get(2)).containsExactly(3, 4);
    }
    @Test
    void allPairsWithDuplicateValues() {
        List<int[]> result = twoSum.twoSumAllPairs(new int[]{1, 2, 3, 4, 5, 6, 3, 4}, 7);
        assertThat(result).hasSize(4);
        assertThat(result.get(0)).containsExactly(1, 6);
        assertThat(result.get(1)).containsExactly(2, 5);
        assertThat(result.get(2)).containsExactly(3, 4);
        assertThat(result.get(3)).containsExactly(3, 4);
    }
    @Test
    void allPairsSameElements() {
        List<int[]> result = twoSum.twoSumAllPairs(new int[]{5, 5, 5, 5}, 10);
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).containsExactly(5, 5);
        assertThat(result.get(1)).containsExactly(5, 5);
    }
    @Test
    void allPairsNoMatch() {
        List<int[]> result = twoSum.twoSumAllPairs(new int[]{1, 2, 3}, 10);
        assertThat(result).isEmpty();
    }
    @Test
    void uniquePairsSkipsDuplicates() {
        List<int[]> result = twoSum.twoSumUniquePairs(new int[]{1, 2, 3, 4, 5, 6, 3, 4}, 7);
        assertThat(result).hasSize(3);
        assertThat(result.get(0)).containsExactly(1, 6);
        assertThat(result.get(1)).containsExactly(2, 5);
        assertThat(result.get(2)).containsExactly(3, 4);
    }
    @Test
    void uniquePairsSameElements() {
        List<int[]> result = twoSum.twoSumUniquePairs(new int[]{5, 5, 5, 5}, 10);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).containsExactly(5, 5);
    }
    @Test
    void uniquePairsNegativeNumbers() {
        List<int[]> result = twoSum.twoSumUniquePairs(new int[]{-3, -1, 0, 1, 2, 4}, 1);
        assertThat(result).hasSize(3);
        assertThat(result.get(0)).containsExactly(-3, 4);
        assertThat(result.get(1)).containsExactly(-1, 2);
        assertThat(result.get(2)).containsExactly(0, 1);
    }
}
