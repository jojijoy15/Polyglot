package com.problems.learning.algo.sorting;

import com.problems.learning.tags.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Medium
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

    /*
        Cycle Decomposition approach — O(n log n) time due to sorting, O(n) space
        (Would be O(n) if values are known to be 1..N, since no sorting needed)

        Key Insight:
            Every permutation is a composition of disjoint cycles.
            Minimum swaps to sort = totalMismatches - numberOfCycles

            Example: [2, 4, 3, 1]
            sorted:  [1, 2, 3, 4]

            Permutation mapping (value → its sorted position):
                2 → pos 1, 4 → pos 3, 3 → pos 2, 1 → pos 0

            Cycles: (0 → 1 → 3 → 0) and (2 → 2)
                Cycle [0,1,3] has length 3 → needs 2 swaps
                Cycle [2] has length 1 → needs 0 swaps (already in place)
                Total swaps = (3-1) + (1-1) = 2

            Formula: swaps = Σ(cycleLength - 1) = mismatches - cycles

        For exactly 2 swaps: mismatches - cycles == 2
    */
    public boolean canSortInTwoSwapsCycleDecomposition(int[] arr) {
        int n = arr.length;
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        // Build mapping: value → set of indices in sorted array
        Map<Integer, java.util.TreeSet<Integer>> valueToSortedIndices = new HashMap<>();
        for (int i = 0; i < n; i++) {
            valueToSortedIndices.computeIfAbsent(sorted[i], k -> new java.util.TreeSet<>()).add(i);
        }

        // Build permutation: perm[i] = where arr[i] should go in the sorted array
        // Strategy: first assign fixed points (perm[i] = i) to minimize cycle count
        int[] perm = new int[n];
        Arrays.fill(perm, -1);

        // Pass 1: assign fixed points — if arr[i] == sorted[i], map i → i
        for (int i = 0; i < n; i++) {
            if (arr[i] == sorted[i]) {
                perm[i] = i;
                valueToSortedIndices.get(arr[i]).remove(i);
            }
        }

        // Pass 2: assign remaining positions from what's left
        for (int i = 0; i < n; i++) {
            if (perm[i] == -1) {
                perm[i] = valueToSortedIndices.get(arr[i]).pollFirst();
            }
        }

        // Count cycles in the permutation
        boolean[] visited = new boolean[n];
        int mismatches = 0;
        int cycles = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i] || perm[i] == i) {
                // Already visited or already in correct position (fixed point)
                continue;
            }

            // Trace the cycle starting from i
            cycles++;
            int j = i;
            while (!visited[j]) {
                visited[j] = true;
                mismatches++;
                j = perm[j];
            }
        }

        // swaps needed = mismatches - cycles
        return (mismatches - cycles) == 2;
    }

    /*
        Optimized Cycle Decomposition — O(n) time, O(n) space
        ONLY works when array contains values 1..N (each value IS its target position)

        No sorting needed because:
            sorted = [1, 2, 3, ..., N]
            perm[i] = arr[i] - 1  (value at index i should go to index value-1)

        Example: [2, 4, 3, 1]
            perm[0] = 2-1 = 1  (value 2 belongs at index 1)
            perm[1] = 4-1 = 3  (value 4 belongs at index 3)
            perm[2] = 3-1 = 2  (value 3 belongs at index 2) ← fixed point
            perm[3] = 1-1 = 0  (value 1 belongs at index 0)

            Cycle: 0→1→3→0 (length 3, needs 2 swaps)
            Fixed: 2→2
            Total swaps = 3 - 1 = 2 ✅
    */
    public boolean canSortInTwoSwapsPositional(int[] arr) {
        int n = arr.length;

        // perm[i] = where arr[i] should go = arr[i] - 1 (0-indexed target)
        boolean[] visited = new boolean[n];
        int mismatches = 0;
        int cycles = 0;

        for (int i = 0; i < n; i++) {
            int target = arr[i] - 1; // value IS the position (1-indexed → 0-indexed)

            if (visited[i] || target == i) {
                // Already visited or already in correct position
                continue;
            }

            // Trace the cycle
            cycles++;
            int j = i;
            while (!visited[j]) {
                visited[j] = true;
                mismatches++;
                j = arr[j] - 1; // follow the permutation
            }
        }

        // swaps needed = mismatches - cycles
        return (mismatches - cycles) == 2;
    }
}
