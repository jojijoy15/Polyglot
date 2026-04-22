package com.problems.learning.algo.dp.fib;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClimbingStairsTest {

    ClimbingStairs test = new ClimbingStairs();

    @Test
    public void testWaysOfClimbingStairs() {
        int ways = test.waysOfClimbingStairs(5);
        assertThat(ways).isEqualTo(8);
    }

    @Test
    public void testWaysOfClimbingStairsLowest() {
        int ways = test.waysOfClimbingStairs(2);
        assertThat(ways).isEqualTo(2);
    }
}