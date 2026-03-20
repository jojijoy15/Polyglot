package com.problems.learning.algo.dp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FibonacciTest {

    Fibonacci fibonacci = new Fibonacci();

    // 0 1 1 2 3 5 8
    @Test
    void loop() {
        int sixthFibo = fibonacci.loop(6);
        assertThat(sixthFibo).isEqualTo(5);
    }

    @Test
    void loopBigFibonacci() {
        int bigFibo = fibonacci.loop(36);
        assertThat(bigFibo).isEqualTo(9_227_465);
    }

    @Test
    void recursive() {
        int fifthFibo = fibonacci.recursive(6);
        assertThat(fifthFibo).isEqualTo(5);
    }

    @Test
    void recursiveBigFibonacci() {
        int fifthFibo = fibonacci.recursive(41);
        assertThat(fifthFibo).isEqualTo(102_334_155);
    }

    @Test
    void dp() {
        int fifthFibo = fibonacci.dp(6);
        assertThat(fifthFibo).isEqualTo(5);
    }

    @Test
    void dpBigFibonacci() {
        int fifthFibo = fibonacci.dp(41);
        assertThat(fifthFibo).isEqualTo(102_334_155);
    }

}