package com.problems.learning.algo.math;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookExchangeTest {

    BookExchange bookExchange = new BookExchange();

    @Test
    void maxPurchaseSingle() {
        int maxBooksPurchased = bookExchange.maxPurchase(5, 2, 10);
        assertThat(maxBooksPurchased).isEqualTo(3);
    }

    @Test
    void maxPurchaseLoop() {
        int maxBooksPurchased = bookExchange.maxPurchase(1, 2, 5);
        assertThat(maxBooksPurchased).isEqualTo(9);
    }
}