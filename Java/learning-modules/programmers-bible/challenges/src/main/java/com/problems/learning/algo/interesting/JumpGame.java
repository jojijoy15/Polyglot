package com.problems.learning.algo.interesting;

public class JumpGame {

    public boolean maxJump(int[] elements) { //greedy
        int destination = elements.length - 1;
        for (int i = elements.length - 2; i >= 0 ; i--) {
            if( i + elements[i] >= destination) {
                destination = i;
            }
        }
        return destination == 0;
    }
}
