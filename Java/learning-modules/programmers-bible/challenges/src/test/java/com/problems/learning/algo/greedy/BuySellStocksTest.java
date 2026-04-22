package com.problems.learning.algo.greedy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuySellStocksTest {

    BuySellStocks buySellStocks = new BuySellStocks();

    @Test
    void maxProfitEven() {
        int[] stockPrices = {7, 1, 5, 3, 6, 4};
        int[] maxSaleDetails = buySellStocks.maxProfit(stockPrices);
        assertThat(maxSaleDetails).containsExactly(5, 1, 4);
    }

    @Test
    void maxProfitOdd() {
        int[] stockPrices = {7, 6, 4, 3, 1};
        int[] maxSaleDetails = buySellStocks.maxProfit(stockPrices);
        assertThat(maxSaleDetails).containsExactly(0, stockPrices.length - 1, -1);
    }
}
