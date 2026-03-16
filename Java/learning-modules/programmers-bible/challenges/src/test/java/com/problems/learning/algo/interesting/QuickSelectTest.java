package com.problems.learning.algo.interesting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuickSelectTest {

    QuickSelect quickSelect = new QuickSelect();

    @Test
    void secondGreatestElementEvenLength() {
        int[] arr = {1, 22, 12, 13, 4, 5};
        int secondGreatestElement = quickSelect.kthGreatestElement(arr, 2);
        assertThat(secondGreatestElement).isEqualTo(13);
    }

    @Test
    void secondGreatestElementOddLength() {
        int[] arr = {111, 15, 2, 12, 1, 4, 5};
        int secondGreatestElement = quickSelect.kthGreatestElement(arr, 2);
        assertThat(secondGreatestElement).isEqualTo(15);
    }
}