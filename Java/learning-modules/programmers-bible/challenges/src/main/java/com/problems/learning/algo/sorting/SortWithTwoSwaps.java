package com.problems.learning.algo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortWithTwoSwaps {

    public boolean canSortInTwoSwaps(int[] arr) {
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        // Find positions where arr differs from sorted
        List<Integer> diff = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != sorted[i]) diff.add(i);
        }

        int d = diff.size();

        // 0 → already sorted (0 swaps needed)
        // 2 → one 2-cycle  (1 swap needed)
        // >4 → needs more than 2 swaps
        if (d < 3 || d > 4) return false;

        // 3 mismatches → always a 3-cycle → exactly 2 swaps
        if (d == 3) return true;

        // 4 mismatches → true only if two independent 2-cycles (not a 4-cycle)
        // Two 2-cycles means: positions pair up such that swapping each pair fixes both
        int a = diff.get(0), b = diff.get(1), c = diff.get(2), e = diff.get(3);
        return (arr[a] == sorted[b] && arr[b] == sorted[a])    // (a,b) + (c,e)
            || (arr[a] == sorted[c] && arr[c] == sorted[a])    // (a,c) + (b,e)
            || (arr[a] == sorted[e] && arr[e] == sorted[a]);   // (a,e) + (b,c)
    }
}
