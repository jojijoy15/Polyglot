package com.problems.learning.algo.simple.binarysearch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LastOccurrenceTest {

    LastOccurrence lastOccurrence = new LastOccurrence();

    @Test
    void foundLastAtValid() {
        int[] arr = { 1, 2, 2, 2, 3, 4, 5 };
        int foundAtLast = lastOccurrence.foundLastAt(arr, 2);
        assertThat(foundAtLast).isEqualTo(3);
    }

    @Test
    void foundLastAtAllSame() {
        int[] arr = { 1, 1, 1, 1, 1 };
        int foundAtLast = lastOccurrence.foundLastAt(arr, 1);
        assertThat(foundAtLast).isEqualTo(4);
    }

    @Test
    void foundLastAtNotFound() {
        int[] arr = { 1, 2, 3, 4, 5};
        int foundAtLast = lastOccurrence.foundLastAt(arr, 6);
        assertThat(foundAtLast).isEqualTo(-1);
    }
}