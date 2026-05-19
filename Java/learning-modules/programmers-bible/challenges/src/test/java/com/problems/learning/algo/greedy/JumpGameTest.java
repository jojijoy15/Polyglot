package com.problems.learning.algo.greedy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JumpGameTest {

    JumpGame jumpGame = new JumpGame();

    @Test
    void canReachEnd() {
        int[] elements = {2,3,1,1,4};
        boolean canJump = jumpGame.canReachEnd(elements);
        assertThat(canJump).isTrue();
    }

    @Test
    void canReachEndNotPossible() {
        int[] elements = {3,2,1,0,4};
        boolean canJump = jumpGame.canReachEnd(elements);
        assertThat(canJump).isFalse();
    }
}