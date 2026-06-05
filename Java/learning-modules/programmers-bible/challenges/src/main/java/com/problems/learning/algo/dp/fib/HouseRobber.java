package com.problems.learning.algo.dp.fib;

import com.problems.learning.tags.Easy;
import com.problems.learning.tags.Medium;

import java.util.Arrays;

@Medium
public class HouseRobber {

    @Easy
    public int rob(int[] treasure) {
        if (treasure == null || treasure.length == 0) {
            return 0;
        }
        int prev = 0, curr = treasure[0];
        for (int i = 2; i <= treasure.length; i++) {
            // calculate the next value of dp
            int take = prev + treasure[i - 1];
            int skip = curr;
            int temp = curr;
            curr = Math.max(take, skip);
            prev = temp;
        }
        return curr;
    }

    /**
     * House Robber II - houses are arranged in a circle (first and last are adjacent).
     * Key insight: we can never rob both house[0] and house[n-1].
     * So answer = max(rob(house[0..n-2]), rob(house[1..n-1]))
     */
    @Medium
    public int robCircular(int[] treasure) {
        if (treasure == null || treasure.length == 0) return 0;
        if (treasure.length == 1) return treasure[0];

        return Math.max(
                robRange(treasure, 0, treasure.length - 2),  // exclude last
                robRange(treasure, 1, treasure.length - 1)   // exclude first
        );
    }

    private int robRange(int[] treasure, int start, int end) {
        int prev = 0, curr = 0;
        for (int i = start; i <= end; i++) {
            int take = prev + treasure[i];
            int skip = curr;
            int temp = curr;
            curr = Math.max(take, skip);
            prev = temp;
        }
        return curr;
    }

    // ==================== RECURSIVE SOLUTIONS ====================

    /**
     * House Robber I - Recursive with memoization
     * At each house: either rob it (skip next) or skip it (move to next)
     */
    public int robRecursive(int[] treasure) {
        if (treasure == null || treasure.length == 0) return 0;
        int[] memo = new int[treasure.length];
        Arrays.fill(memo, -1);
        return robHelper(treasure, 0, memo);
    }

    private int robHelper(int[] treasure, int i, int[] memo) {
        if (i >= treasure.length) return 0;
        if (memo[i] != -1) return memo[i];

        int take = treasure[i] + robHelper(treasure, i + 2, memo); // rob this, skip next
        int skip = robHelper(treasure, i + 1, memo);               // skip this
        memo[i] = Math.max(take, skip);
        return memo[i];
    }

    /**
     * House Robber II - Recursive with memoization (circular)
     * Split into two subproblems: exclude last house OR exclude first house
     */
    public int robCircularRecursive(int[] treasure) {
        if (treasure == null || treasure.length == 0) return 0;
        if (treasure.length == 1) return treasure[0];

        int[] memo1 = new int[treasure.length];
        int[] memo2 = new int[treasure.length];
        Arrays.fill(memo1, -1);
        Arrays.fill(memo2, -1);

        return Math.max(
                robCircularHelper(treasure, 0, treasure.length - 2, memo1),  // exclude last
                robCircularHelper(treasure, 1, treasure.length - 1, memo2)   // exclude first
        );
    }

    private int robCircularHelper(int[] treasure, int i, int end, int[] memo) {
        if (i > end) return 0;
        if (memo[i] != -1) return memo[i];

        int take = treasure[i] + robCircularHelper(treasure, i + 2, end, memo);
        int skip = robCircularHelper(treasure, i + 1, end, memo);
        memo[i] = Math.max(take, skip);
        return memo[i];
    }
}
