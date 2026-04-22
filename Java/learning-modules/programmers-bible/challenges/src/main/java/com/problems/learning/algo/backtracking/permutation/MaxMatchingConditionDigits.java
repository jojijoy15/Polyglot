package com.problems.learning.algo.backtracking.permutation;

import com.problems.learning.tags.Hard;

import java.util.*;

@Hard
public class MaxMatchingConditionDigits {

    // Generate all unique digit permutations of a number using TreeSet for O(log n) lookup
    static TreeSet<Integer> getPermutations(int num) {
        TreeSet<Integer> result = new TreeSet<>();
        char[] digits = String.valueOf(num).toCharArray();
        permute(digits, 0, result);
        return result;
    }

    static void permute(char[] digits, int start, Set<Integer> result) {
        if (start == digits.length) {
            result.add(Integer.parseInt(new String(digits)));
            return;
        }
        Set<Character> used = new HashSet<>(); // prune duplicate digit swaps
        for (int i = start; i < digits.length; i++) {
            if (used.add(digits[i])) {
                swap(digits, start, i);
                permute(digits, start + 1, result);
                swap(digits, start, i);
            }
        }
    }

    static void swap(char[] arr, int i, int j) {
        char tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    static int maxMatchingConditions(int[] arr) {
        int n = arr.length;

        // Step 1: generate all permutations per element (TreeSet for O(log n) lookup)
        List<TreeSet<Integer>> allPerms = new ArrayList<>();
        int[] minPerms = new int[n]; // cache min values for sorting
        for (int i = 0; i < n; i++) {
            TreeSet<Integer> perms = getPermutations(arr[i]);
            allPerms.add(perms);
            minPerms[i] = perms.first();
        }

        // Step 2: sort indices by cached min permutation value
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        Arrays.sort(indices, Comparator.comparingInt(i -> minPerms[i]));

        // Step 3: greedy — use TreeSet.higher() for O(log n) lookup of smallest value > prev
        int count = 0;
        int prev = -1;

        for (int idx : indices) {
            TreeSet<Integer> perms = allPerms.get(idx);
            Integer chosen = perms.higher(prev); // smallest value strictly > prev, O(log n)

            if (chosen == null) {
                prev = perms.first(); // no valid form, use smallest
            } else {
                if (prev != -1) count++;
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