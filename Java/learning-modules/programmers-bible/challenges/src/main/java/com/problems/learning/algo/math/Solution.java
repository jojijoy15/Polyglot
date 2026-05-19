package com.problems.learning.algo.math;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    public int findJudge(int n, int[][] trust) {
        Map<Integer, Integer> trustHolder = new HashMap<>();
        for(int[] pt: trust) {
            trustHolder.merge(pt[1], 1, Integer::sum);
        }
        for(Map.Entry<Integer, Integer> entry : trustHolder.entrySet()) {
            if(entry.getValue().equals(n-1))
                return entry.getKey();
        }
        return -1;
    }
}