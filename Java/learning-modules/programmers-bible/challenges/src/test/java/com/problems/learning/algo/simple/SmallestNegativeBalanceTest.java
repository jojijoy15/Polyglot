package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SmallestNegativeBalanceTest {

    SmallestNegativeBalance smallestNegativeBalance = new SmallestNegativeBalance();

    @Test
    void findWorstBorrower() {
        String[][] transactions = new String[][]{
                {"Alex", "Blake", "2"},
                {"Blake", "Alex", "2"},
                {"Casey", "Alex", "5"},
        };
        List<String> worstBorrower = smallestNegativeBalance.findWorstBorrower(transactions);
        assertThat(worstBorrower).containsExactly("Casey");
    }

    @Test
    void findWorstBorrowerNone() {
        String[][] transactions = new String[][]{
                {"Alex", "Blake", "2"},
                {"Blake", "Alex", "2"},
        };
        List<String> worstBorrower = smallestNegativeBalance.findWorstBorrower(transactions);
        assertThat(worstBorrower).isEqualTo(List.of("Nobody has a negative balance"));
    }
}