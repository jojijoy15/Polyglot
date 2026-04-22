package com.problems.learning.algo.search.binarysearch;

public class RotatedArrayMin {

    // O(log n) time | O(1) space
    public static int findMin(int[] arr) {

        int lo = 0;
        int hi = arr.length - 1;

        // Already sorted — no rotation
        if (arr[lo] <= arr[hi]) return arr[lo];

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2; // avoids integer overflow

            if (arr[mid] > arr[hi]) {
                // Min is in right half — exclude mid
                lo = mid + 1;
            } else {
                // Min is in left half — include mid (it could be the min)
                hi = mid;
            }
        }

        return arr[lo]; // lo == hi → converged on minimum
    }
}
