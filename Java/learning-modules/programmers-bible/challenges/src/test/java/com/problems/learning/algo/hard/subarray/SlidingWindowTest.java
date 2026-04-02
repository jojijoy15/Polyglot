package com.problems.learning.algo.hard.subarray;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SlidingWindowTest {

    SlidingWindow slidingWindow = new SlidingWindow();

    @Test
    void maxSlidingWindowEven() {
        int[] elements = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] maxWindows = slidingWindow.maxSlidingWindow(elements, 3);
        assertThat(maxWindows).containsExactly(3, 3, 5, 5, 6, 7);
    }

    @Test
    void maxSlidingWindowOdd() {
        int[] elements = new int[]{11, 33, 0, 12, 51, 3, 16, 7, 9};
        int[] maxWindows = slidingWindow.maxSlidingWindow(elements, 3);
        assertThat(maxWindows).containsExactly(33, 33, 51, 51, 51, 16, 16);
    }

    @Test
    void maxSlidingWindowEvenOptimized() {
        int[] elements = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] maxWindows = slidingWindow.maxSlidingWindowOptimized(elements, 3);
        assertThat(maxWindows).containsExactly(3, 3, 5, 5, 6, 7);
    }

    @Test
    void maxSlidingWindowOddOptimized() {
        int[] elements = new int[]{11, 33, 0, 12, 51, 3, 16, 7, 9};
        int[] maxWindows = slidingWindow.maxSlidingWindowOptimized(elements, 3);
        assertThat(maxWindows).containsExactly(33, 33, 51, 51, 51, 16, 16);
    }
}