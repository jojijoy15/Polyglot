package com.problems.learning.algo.search.binarysearch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MissingNumberTest {


    MissingNumber missingNumber = new MissingNumber();

    @Test
    void missingInMiddle() {
        // [1,2,3,5,6] → missing 4 from range 1..6
        int result = missingNumber.findMissing(new int[]{1, 2, 3, 5, 6});
        assertThat(result).isEqualTo(4);
    }

    @Test
    void missingFirst() {
        // [2,3,4,5] → missing 1 from range 1..5
        int result = missingNumber.findMissing(new int[]{2, 3, 4, 5});
        assertThat(result).isEqualTo(1);
    }

    @Test
    void missingLast() {
        // [1,2,3,4] → missing 5 from range 1..5
        int result = missingNumber.findMissing(new int[]{1, 2, 3, 4});
        assertThat(result).isEqualTo(5);
    }

    @Test
    void missingFromTwo() {
        // [2] → missing 1 from range 1..2
        int result = missingNumber.findMissing(new int[]{2});
        assertThat(result).isEqualTo(1);
    }

    @Test
    void missingSecondFromTwo() {
        // [1] → missing 2 from range 1..2
        int result = missingNumber.findMissing(new int[]{1});
        assertThat(result).isEqualTo(2);
    }

    @Test
    void missingInLargeArray() {
        // [1,2,3,4,5,6,7,8,10] → missing 9 from range 1..10
        int result = missingNumber.findMissing(new int[]{1, 3, 4, 5, 6, 7, 8, 9, 10});
        assertThat(result).isEqualTo(2);
    }
}