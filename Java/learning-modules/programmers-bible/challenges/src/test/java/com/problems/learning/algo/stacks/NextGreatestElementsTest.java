package com.problems.learning.algo.stacks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class NextGreatestElementsTest {

    NextGreatestElements ms = new NextGreatestElements();

    @Test
    void nextGreatestElements() {
        int[] input = new int[]{5, 10, 6, 8, 6};
        int[] ints = ms.nextGreatestElements(input);
        assertThat(ints).containsExactly(10, -1, 8, -1, -1);
    }

    @Test
    void nextGreatestElementsDescending() {
        int[] input = new int[]{5, 4, 3, 2, 1};
        int[] ints = ms.nextGreatestElements(input);
        assertThat(ints).containsExactly(-1, -1, -1, -1, -1);
    }

    @Test
    void nextGreatestElementsAscending() {
        int[] input = new int[]{1, 2, 3, 4, 5};
        int[] ints = ms.nextGreatestElements(input);
        assertThat(ints).containsExactly(2, 3, 4, 5, -1);
    }


    @Test
    void nextGreatestElementsEmpty() {
        int[] input = new int[]{};
        int[] ints = ms.nextGreatestElements(input);
        assertThat(ints).containsExactly();

    }
}