package com.problems.learning.algo.greedy;

import java.util.Arrays;

public class JumpGame {

    public boolean canReachEnd(int[] elements) { //greedy
        int destination = elements.length - 1;
        for (int i = elements.length - 2; i >= 0 ; i--) { // Start from destination
            if( i + elements[i] >= destination) {       // check if I can reach the destination
                destination = i;                        // Update destination if, can reach it
            }
        }
        return destination == 0;                        // Check if reached the beginning
    }

    /*
     * DP approach for canReachEnd.
     * dp[i] = can we reach the last index from index i?
     *
     * Base case: dp[n-1] = true (already at end)
     * Transition: dp[i] = true if any dp[j] == true for j in [i+1, i+nums[i]]
     *
     * Time: O(n²), Space: O(n)
     */
    public boolean canReachEndDP(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[n - 1] = true;

        for (int i = n - 2; i >= 0; i--) {
            int farthest = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= farthest; j++) {
                if (dp[j]) {
                    dp[i] = true;
                    break; // no need to check further
                }
            }
        }

        return dp[0];
    }

    /*
        You are given a 0-indexed array of integers nums of length n. You are initially positioned at index 0.
        Each element nums[i] represents the maximum length of a forward jump from index i. In other words, if you are at index i, you can jump to any index (i + j) where:
        0 <= j <= nums[i] and
        i + j < n
        Return the minimum number of jumps to reach index n - 1. The test cases are generated such that you can reach index n - 1.
     */
    public int jumpGame2Greedy(int[] nums) {
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            if (i == currentEnd) {       // reached boundary → must jump
                jumps++;
                currentEnd = farthest;   // extend to farthest reachable
            }
        }

        return jumps;
    }

    public int jumpGame2Dynamic(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;   // 0 jumps needed to reach index 0

        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j < n) {
                    dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
                }
            }
        }

        return dp[n - 1];
    }


}
