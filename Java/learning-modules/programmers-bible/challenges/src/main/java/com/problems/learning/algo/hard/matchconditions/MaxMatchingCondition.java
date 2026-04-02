package com.problems.learning.algo.hard.matchconditions;

import java.util.*;

public class MaxMatchingCondition {

    //TODO Is it greedy?
    public int maxMatches(int[] arr) {
        if (arr == null || arr.length <= 1) return 0;

        int n = arr.length;

        // Step 1: Count frequencies to find maxFrequency
        Map<Integer, Integer> freqMap = new HashMap<>();
        int maxFreq = 0;
        for (int num : arr) {
            int count = freqMap.merge(num, 1, Integer::sum);
            maxFreq = Math.max(maxFreq, count);
        }

        // Step 2: Sort the array
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        //T: 5 R: 2
        int cols = (n + maxFreq - 1) / maxFreq; // ceiling division, gives non repeating elements i.e partition
        int[] arranged = new int[n];
        int idx = 0;
        for (int row = 0; row < maxFreq; row++) {
            for (int col = 0; col < cols; col++) {
                int srcIndex = col * maxFreq + row; //Calculate 2d index in 1D form
                if (srcIndex < n) {
                    arranged[idx++] = sorted[srcIndex];
                }
            }
        }

        // Step 4: Count satisfied conditions
        int count = 0;
        for (int i = 0; i < n - 1; i++) {
            if (arranged[i] < arranged[i + 1]) {
                count++;
            }
        }
        System.out.println("Arranged: " + Arrays.toString(arranged));
        return count;
    }

}