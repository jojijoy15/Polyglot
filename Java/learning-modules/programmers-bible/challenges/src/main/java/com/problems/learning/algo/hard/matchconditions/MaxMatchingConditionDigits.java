package com.problems.learning.algo.hard.matchconditions;

import java.util.*;

public class MaxMatchingConditionDigits {

    // Generate all unique digit permutations of a number
    static Set<Integer> getPermutations(int num) {
        Set<Integer> result = new HashSet<>();
        char[] digits = String.valueOf(num).toCharArray();
        permute(digits, 0, result);
        return result;
    }

    static void permute(char[] digits, int start, Set<Integer> result) {
        if (start == digits.length) {
            // Build number, skip leading zeros
            String s = new String(digits);
            int val = Integer.parseInt(s);  // "03" becomes 3 automatically
            result.add(val);
            return;
        }
        for (int i = start; i < digits.length; i++) {
            swap(digits, start, i);
            permute(digits, start + 1, result);
            swap(digits, start, i);
        }
    }

    static void swap(char[] arr, int i, int j) {
        char tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    static int maxMatchingConditions(int[] arr) {
        int n = arr.length;

        // Step 1: generate all permutations per element
        List<Set<Integer>> allPerms = new ArrayList<>();
        for (int num : arr) {
            allPerms.add(getPermutations(num));
        }

        // Step 2: sort elements by their MINIMUM digit permutation
        // Pair each index with its permutations, sort by min value
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        Arrays.sort(indices, (a, b) -> {
            int minA = Collections.min(allPerms.get(a));
            int minB = Collections.min(allPerms.get(b));
            return Integer.compare(minA, minB);
        });

        // Step 3: greedy assignment — pick smallest valid form > prev
        int count = 0;
        int prev = -1;

        for (int idx : indices) {
            Set<Integer> perms = allPerms.get(idx);
            // Sort permutations ascending
            List<Integer> sorted = new ArrayList<>(perms);
            Collections.sort(sorted);

            int chosen = -1;
            for (int val : sorted) {
                if (val > prev) {
                    chosen = val;  // smallest value that satisfies arr[i-1] < arr[i]
                    break;
                }
            }

            if (chosen == -1) {
                // No valid form found — just use the smallest form, skip pair
                prev = sorted.get(0);
            } else {
                if (prev != -1) count++;  // pair satisfied
                prev = chosen;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[] arr = {21, 3, 15, 27, 14};
        System.out.println("Input:  " + Arrays.toString(arr));
        System.out.println("Output: " + maxMatchingConditions(arr));

        // Trace the optimal assignment
        System.out.println("\nOptimal arrangement:");
        System.out.println("3  <  12  <  15  <  27  <  41");
        System.out.println("    ✓      ✓      ✓      ✓");
        System.out.println("Max matching pairs = 4");
    }
}