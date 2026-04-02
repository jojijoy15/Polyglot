package com.problems.learning.algo.simple.zerosOnes;

import java.util.ArrayList;
import java.util.List;

public class GroupMinMoves {

    /*
        Minimum number of moves required to move all 1s together
        Input: 0 0 1 0 0 0 0 1 1 0 0 0 1 0 0 0
     */
    public int minMoves(int[] arr) {
        List<Integer> pos = new ArrayList<>();

        // Step 1: collect indices of 1s
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                pos.add(i);
            }
        }

        int k = pos.size();
        if (k <= 1) return 0;

        // Step 2: normalize
        List<Integer> normalized = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            normalized.add(pos.get(i) - i);
        }

        // Step 3: median
        int median = normalized.get(k / 2);

        // Step 4: compute moves
        int moves = 0;
        for (int val : normalized) {
            moves += Math.abs(val - median);
        }

        return moves;
    }
}
