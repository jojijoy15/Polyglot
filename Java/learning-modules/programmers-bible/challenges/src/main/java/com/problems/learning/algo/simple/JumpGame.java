package com.problems.learning.algo.simple;

public class JumpGame {

    public boolean maxJump(int[] elements) { //greedy
        int destination = elements.length - 1;
        for (int i = elements.length - 2; i >= 0 ; i--) { // Start from destination
            if( i + elements[i] >= destination) {       // check if I can reach the destination
                destination = i;                        // Update destination if, can reach it
            }
        }
        return destination == 0;                        // Check if reached the beginning
    }
}
