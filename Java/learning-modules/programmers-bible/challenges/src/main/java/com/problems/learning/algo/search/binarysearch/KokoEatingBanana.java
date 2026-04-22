package com.problems.learning.algo.search.binarysearch;

public class KokoEatingBanana {

    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 0;

        // find max pile
        for (int p : piles) {
            right = Math.max(right, p);
        }

        int result = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            long hours = calculateHours(piles, mid);

            if (hours <= h) {
                result = mid;
                right = mid - 1; // try smaller speed
            } else {
                left = mid + 1;  // need faster speed
            }
        }

        return result;
    }

    private long calculateHours(int[] piles, int k) {
        long hours = 0;

        for (int p : piles) {
            hours += (p + k - 1) / k; // ceil(p / k)
        }

        return hours;
    }
}
