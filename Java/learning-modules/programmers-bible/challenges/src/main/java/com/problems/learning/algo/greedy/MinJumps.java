package com.problems.learning.algo.greedy;

import com.problems.learning.tags.Easy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

@Easy
public class MinJumps {

    //Greedy
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (arr[0] == 0)
            return -1;
        int maxReach = 0;
        int currReach = 0;
        int jump = 0;
        for (int i = 0; i < n; i++) {
            maxReach = Math.max(maxReach, i + arr[i]);

            if (maxReach >= n - 1) {
                return jump + 1;
            }

            if (i == currReach) {

                if (i == maxReach) {
                    return -1;
                } else {
                    jump++;
                    currReach = maxReach;
                }
            }
        }
        return -1;
    }

    //DP
    int minJumpsDP(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[n - 1] = 0;

        for (int i = n - 2; i >= 0; i--) {
            // Try all reachable positions from i
            for (int j = i + 1; j <= i + arr[i] && j < n; j++) {
                if (dp[j] != Integer.MAX_VALUE)
                    dp[i] = Math.min(dp[i], 1 + dp[j]);
            }
        }

        return (dp[0] == Integer.MAX_VALUE) ? -1 : dp[0];
    }

    // Brute Force (Iterative BFS - tries all reachable positions level by level)
    int minJumpsBruteForce(int[] arr) {
        int n = arr.length;
        if (n <= 1) return 0;

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.add(0);
        visited[0] = true;
        int jumps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            jumps++;
            for (int q = 0; q < size; q++) {
                int pos = queue.poll();
                // Try every jump length from 1 to arr[pos]
                for (int jump = 1; jump <= arr[pos]; jump++) {
                    int next = pos + jump;
                    if (next >= n - 1) return jumps;
                    if (!visited[next]) {
                        visited[next] = true;
                        queue.add(next);
                    }
                }
            }
        }
        return -1;
    }

}

