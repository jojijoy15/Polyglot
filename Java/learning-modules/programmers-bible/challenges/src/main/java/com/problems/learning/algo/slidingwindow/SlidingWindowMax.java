package com.problems.learning.algo.slidingwindow;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMax {

    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        int maxOfWindows = (nums.length - windowSize) + 1;
        int[] res = new int[maxOfWindows];
        for (int left = 0, w = 0; left < nums.length - windowSize + 1; left++) {
            int maxOfCurrentWindow = 0;
            for (int right = left; right < left + windowSize; right++) {
                maxOfCurrentWindow = Math.max(maxOfCurrentWindow, nums[right]);
            }
            res[w++] = maxOfCurrentWindow;
        }
        return res;
    }


    public int[] maxSlidingWindowOptimized(int[] nums, int k) {
        int n = nums.length;
        int totalWindows = (n - k) + 1;
        int[] result = new int[totalWindows];
        Deque<Integer> deque = new ArrayDeque<>(); // stores indices

        for (int i = 0; i < n; i++) {

            //Check window size
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) { // Found a smaller element, decreasing deque
                deque.pollLast();
            }

            deque.offerLast(i);
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

}
