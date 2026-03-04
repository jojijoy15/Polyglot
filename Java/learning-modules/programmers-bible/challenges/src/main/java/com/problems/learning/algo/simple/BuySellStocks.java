package com.problems.learning.algo.simple;

public class BuySellStocks {

    //Kadane's Algorithm
    public int[] maxProfit(int[] prices) {
        int maxProfit = 0;
        int buy = prices[0];
        int buyIndex = 0; int sellIndex = -1;
        for(int i = 1; i < prices.length; i++){
            if(prices[i] < buy){
                buy = prices[i];
                buyIndex = i;
            } else if(prices[i] - buy > maxProfit){
                maxProfit = prices[i] - buy;
                sellIndex = i;
            }
        }
        return new int[]{maxProfit, buyIndex , sellIndex};
    }
}
