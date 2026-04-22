package com.problems.learning.algo.stacks.monotonic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NextSmallestElementsTest {

    NextSmallestElements nextSmallestElements = new NextSmallestElements();

    @Test
    void nextSmallestElements() {
        int[] input = new int[]{5, 10, 4, 6, 8, 6};
        int[] ints = nextSmallestElements.nextSmallest(input);
        assertThat(ints).containsExactly(4, 4, -1, -1, 6, -1);
    }

    @Test
    void nextSmallestElements2() {
        int[] input = new int[]{5, 7, 3, 6, 1, 2, 4};
        int[] ints = nextSmallestElements.nextSmallest(input);
        assertThat(ints).containsExactly(3, 3, 1, 1, -1, -1, -1);
    }

    @Test
    void nextSmallestElementsDescending() {
        int[] input = new int[]{5, 4, 3, 2, 1};
        int[] ints = nextSmallestElements.nextSmallest(input);
        assertThat(ints).containsExactly(4, 3, 2, 1, -1);
    }

    @Test
    void nextSmallestElementsAscending() {
        int[] input = new int[]{1, 2, 3, 4, 5};
        int[] ints = nextSmallestElements.nextSmallest(input);
        assertThat(ints).containsExactly(-1, -1, -1, -1, -1);
    }

    @Test
    void nextSmallestElementsEmpty() {
        int[] input = new int[]{};
        int[] ints = nextSmallestElements.nextSmallest(input);
        assertThat(ints).containsExactly();
    }

}