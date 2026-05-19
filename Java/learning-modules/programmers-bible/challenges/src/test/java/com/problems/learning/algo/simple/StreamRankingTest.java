package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StreamRankingTest {

    @Test
    void rankStream_givenExample() {
        StreamRanking ranking = new StreamRanking();
        //  Input:  15  20  36  45  25  36   25  80  36
        //  Output:  1   1   1   1   3   3    5   1   5
        int[] result = ranking.rankStream(new int[]{15, 20, 36, 45, 25, 36, 25, 80, 36});
        assertThat(result).containsExactly(1, 1, 1, 1, 3, 3, 5, 1, 5);
    }

    @Test
    void rankStream_singleElement() {
        StreamRanking ranking = new StreamRanking();
        assertThat(ranking.rankStream(new int[]{42})).containsExactly(1);
    }

    @Test
    void rankStream_allSame() {
        StreamRanking ranking = new StreamRanking();
        // 5 → rank 1, 5 → rank 2, 5 → rank 3
        assertThat(ranking.rankStream(new int[]{5, 5, 5})).containsExactly(1, 2, 3);
    }

    @Test
    void rankStream_ascending() {
        StreamRanking ranking = new StreamRanking();
        // 1 → rank 1, 2 → rank 1, 3 → rank 1
        assertThat(ranking.rankStream(new int[]{1, 2, 3})).containsExactly(1, 1, 1);
    }

    @Test
    void rankStream_descending() {
        StreamRanking ranking = new StreamRanking();
        // 3 → rank 1, 2 → rank 2, 1 → rank 3
        assertThat(ranking.rankStream(new int[]{3, 2, 1})).containsExactly(1, 2, 3);
    }

    @Test
    void addAndRank_incrementally() {
        StreamRanking ranking = new StreamRanking();
        assertThat(ranking.addAndRank(15)).isEqualTo(1);
        assertThat(ranking.addAndRank(20)).isEqualTo(1);
        assertThat(ranking.addAndRank(36)).isEqualTo(1);
        assertThat(ranking.addAndRank(45)).isEqualTo(1);
        assertThat(ranking.addAndRank(25)).isEqualTo(3);
        assertThat(ranking.addAndRank(36)).isEqualTo(3);
        assertThat(ranking.addAndRank(25)).isEqualTo(5);
        assertThat(ranking.addAndRank(80)).isEqualTo(1);
        assertThat(ranking.addAndRank(36)).isEqualTo(5);
    }
}

