package com.problems.learning.algo.dp.sequence;

import java.util.Arrays;

public class LongestIncreasingSubSequence {

    // FIXME revisit this and fix the logic
    public int longestIncreasingSubsequence(String input) {
        int n = input.length();
        if (n == 0) return 0;

        int[] dp = new int[n];
        int[] next = new int[n]; // tracks the next index in the subsequence
        Arrays.fill(dp, 1);
        Arrays.fill(next, -1);

        int bestLen = 1;
        int bestStart = 0;

        for (int i = n - 1; i >= 0; i--) { // Starting from last
            for (int j = i + 1; j < n; j++) {
                if (input.charAt(i) < input.charAt(j) && dp[i] < 1 + dp[j]) { //Track when it is low
                    dp[i] = 1 + dp[j];
                    next[i] = j; // record which index comes next
                }
            }
            if (dp[i] > bestLen) {
                bestLen = dp[i];
                bestStart = i;
            }
        }

        System.out.println("DP:   " + Arrays.toString(dp));
        System.out.println("Next: " + Arrays.toString(next));
        System.out.println("Subsequence: " + reconstructSubsequence(input, next, bestStart));

        return bestLen;
    }

    private String reconstructSubsequence(String input, int[] next, int bestStart) {
        StringBuilder sb = new StringBuilder();
        for (int i = bestStart; i != -1; i = next[i]) {
            sb.append(input.charAt(i));
        }
        return sb.toString();
    }


}
