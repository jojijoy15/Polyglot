package com.problems.learning.algo.dp.fib;

import com.problems.learning.tags.Easy;

@Easy(
        trick = "Fibonacci and DP problem"
)
public class ClimbingStairs {

    public int waysOfClimbingStairs(int n) {
        int a = 1;
        int b = 1;

        for (int i = 1; i < n; i++) {
            int temp = b;
            b = a + b;
            a = temp;
        }
        return b;
    }

    public int minCostClimbingStairs(int[] cost) {
        int first = cost[0];
        int second = cost[1];

        for (int i = 2; i < cost.length; i++) {
            int temp = Math.min(first, second) + cost[i];
            first = second;
            second = temp;
        }
        return Math.min(first, second);
    }
}
