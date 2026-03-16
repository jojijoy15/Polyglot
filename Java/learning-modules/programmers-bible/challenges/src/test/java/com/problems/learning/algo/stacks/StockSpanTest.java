package com.problems.learning.algo.stacks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StockSpanTest {

    StockSpan stockSpan = new StockSpan();

    @Test
    void calculateStockSpan() {
        int[] elements = new int[] {100, 80, 60, 70, 60, 75, 85};
        int[] calculatedStockSpan = stockSpan.calculateStockSpan(elements);
        assertThat(calculatedStockSpan).containsExactly(1, 1, 1, 2, 1, 4, 6);
    }
}