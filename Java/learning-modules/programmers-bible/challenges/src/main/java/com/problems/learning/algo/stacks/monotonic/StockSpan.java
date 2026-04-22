package com.problems.learning.algo.stacks.monotonic;

import java.util.Stack;

public class StockSpan {

    /*
    Problem: Stock Span Problem

        Given the price of a stock for N consecutive days, the stock span for a given day
        is defined as the maximum number of consecutive days (up to and including the current day)
        for which the price of the stock on the current day is less than or equal to its price on those days.
    */
    public int[] calculateStockSpan(int[] stockPrices) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[stockPrices.length];
        for (int i = 0; i < stockPrices.length; i++) {
            while (!stack.isEmpty() && stockPrices[stack.peek()] <= stockPrices[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                result[i] = i + 1;
            } else {
                result[i] = i - stack.peek();
            }
            stack.push(i);
        }
        return result;
    }
}
