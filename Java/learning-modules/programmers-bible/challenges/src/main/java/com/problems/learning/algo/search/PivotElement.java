package com.problems.learning.algo.search;

import com.problems.learning.tags.Medium;

@Medium
public class PivotElement {

    /*
        Find pivot element in an array where:
        Case 1 (Peak): all elements before it are strictly ascending, all after are strictly descending
        Case 2 (Valley): all elements before it are strictly descending, all after are strictly ascending

        Pivot cannot be first or last element (needs elements on both sides).
        Purely ascending or descending arrays have no pivot → return -1.

        Examples:
            [1,2,3,4,5] → -1 (purely ascending)
            [5,4,3,2,1] → -1 (purely descending)
            [5,4,1,2,3] → 1  (valley: [5,4] desc, [2,3] asc)
            [1,2,5,4,3] → 5  (peak:   [1,2] asc, [4,3] desc)

        Approach: O(n) — single pass to find the transition point
            1. Walk from left while ascending → find peak candidate
            2. Walk from left while descending → find valley candidate
            3. Verify the other half
     */
    public int findPivot(int[] arr) {
        if (arr == null || arr.length < 3) return -1;

        int n = arr.length;

        // Find where the initial trend changes
        // Determine initial direction
        int i = 0;
        while (i < n - 1 && arr[i] == arr[i + 1]) i++; // skip equal elements at start
        if (i >= n - 1) return -1; // all elements equal

        boolean startsAscending = arr[i] < arr[i + 1];

        if (startsAscending) {
            // Walk ascending
            int pivot = 0;
            while (pivot < n - 1 && arr[pivot] < arr[pivot + 1]) {
                pivot++;
            }
            // pivot is at the peak — must not be first or last
            if (pivot == 0 || pivot == n - 1) return -1;
            // Verify everything after is descending
            for (int j = pivot; j < n - 1; j++) {
                if (arr[j] <= arr[j + 1]) return -1;
            }
            return arr[pivot];
        } else {
            // Walk descending
            int pivot = 0;
            while (pivot < n - 1 && arr[pivot] > arr[pivot + 1]) {
                pivot++;
            }
            // pivot is at the valley — must not be first or last
            if (pivot == 0 || pivot == n - 1) return -1;
            // Verify everything after is ascending
            for (int j = pivot; j < n - 1; j++) {
                if (arr[j] >= arr[j + 1]) return -1;
            }
            return arr[pivot];
        }
    }
}

