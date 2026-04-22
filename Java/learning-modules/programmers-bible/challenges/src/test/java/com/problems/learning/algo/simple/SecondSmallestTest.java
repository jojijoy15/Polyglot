package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecondSmallestTest {

    SecondSmallest smallest = new SecondSmallest();

    @Test
    public void secondSmallestTest(){
        int[] elements = {10, 4, -2, 2, 10, 11, 17, 4};
        int secondSmallest = smallest.secondSmallest(elements);
        assertThat(secondSmallest).isEqualTo(2);
    }
}