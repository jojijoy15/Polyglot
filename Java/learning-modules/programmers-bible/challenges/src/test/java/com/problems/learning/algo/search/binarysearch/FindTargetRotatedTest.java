package com.problems.learning.algo.search.binarysearch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindTargetRotatedTest {

    FindTargetRotated findTargetRotated =  new FindTargetRotated();

    @Test
    void findTargetOdd() {
        int[] rotated = {6 ,7, 18 , 1, 3, 4, 5};
        int target = findTargetRotated.findTarget(rotated, 3);
        assertThat(target).isEqualTo(4);

    }

    @Test
    void findTargetEven() {
        int[] rotated = {6 ,7, 18, 3, 4, 5};
        int target = findTargetRotated.findTarget(rotated, 3);
        assertThat(target).isEqualTo(3);

    }
}