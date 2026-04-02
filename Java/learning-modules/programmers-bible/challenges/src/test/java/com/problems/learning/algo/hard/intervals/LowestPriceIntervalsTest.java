package com.problems.learning.algo.hard.intervals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LowestPriceIntervalsTest {
    LowestPriceIntervals solver = new LowestPriceIntervals();

    @Test
    void givenExample() {
        // Offers: [1,7,15], [5,7,10], [3,5,5]
        // Timeline: [1,3)=15, [3,5)=5, [5,7)=10
        int[][] offers = {{1, 7, 15}, {5, 7, 10}, {3, 5, 5}};
        int[][] result = solver.getLowestPrices(offers);
        assertThat(result).isDeepEqualTo(new int[][]{
                {1, 3, 15},
                {3, 5, 5},
                {5, 7, 10}
        });
    }

    @Test
    void nonOverlappingOffers() {
        // No overlap: each time has only one price
        int[][] offers = {{1, 3, 10}, {5, 7, 20}, {8, 10, 5}};
        int[][] result = solver.getLowestPrices(offers);
        assertThat(result).isDeepEqualTo(new int[][]{
                {1, 3, 10},
                {5, 7, 20},
                {8, 10, 5}
        });
    }

    @Test
    void fullyOverlappingOffers() {
        // Same time range, different prices: lowest wins
        int[][] offers = {{1, 5, 20}, {1, 5, 10}, {1, 5, 30}};
        int[][] result = solver.getLowestPrices(offers);
        assertThat(result).isDeepEqualTo(new int[][]{
                {1, 5, 10}
        });
    }

    @Test
    void singleOffer() {
        int[][] offers = {{2, 8, 25}};
        int[][] result = solver.getLowestPrices(offers);
        assertThat(result).isDeepEqualTo(new int[][]{
                {2, 8, 25}
        });
    }

    @Test
    void emptyInput() {
        int[][] result = solver.getLowestPrices(new int[][]{});
        assertThat(result).isEmpty();
    }

    @Test
    void adjacentIntervalsWithSamePriceMerge() {
        // [1,3) price 10 and [3,5) price 10 should merge into [1,5,10]
        int[][] offers = {{1, 3, 10}, {3, 5, 10}};
        int[][] result = solver.getLowestPrices(offers);
        assertThat(result).isDeepEqualTo(new int[][]{
                {1, 5, 10}
        });
    }

    @Test
    void cheaperOfferInMiddle() {
        // [1,10] at price 20, [4,6] at price 5
        int[][] offers = {{1, 10, 20}, {4, 6, 5}};
        int[][] result = solver.getLowestPrices(offers);
        assertThat(result).isDeepEqualTo(new int[][]{
                {1, 4, 20},
                {4, 6, 5},
                {6, 10, 20}
        });
    }

    @Test
    void multiplePriceChanges() {
        // [1,10,30], [2,8,20], [4,6,10]
        int[][] offers = {{1, 10, 30}, {2, 8, 20}, {4, 6, 10}};
        int[][] result = solver.getLowestPrices(offers);
        assertThat(result).isDeepEqualTo(new int[][]{
                {1, 2, 30},
                {2, 4, 20},
                {4, 6, 10},
                {6, 8, 20},
                {8, 10, 30}
        });
    }

    @Test
    void duplicatePrices() {
        // Two offers with same price at same time
        int[][] offers = {{1, 5, 10}, {1, 5, 10}};
        int[][] result = solver.getLowestPrices(offers);
        assertThat(result).isDeepEqualTo(new int[][]{
                {1, 5, 10}
        });
    }
}
