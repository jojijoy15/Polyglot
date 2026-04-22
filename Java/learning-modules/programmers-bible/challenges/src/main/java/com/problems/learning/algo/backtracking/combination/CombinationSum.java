package com.problems.learning.algo.backtracking.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {

    /*
        Given an array arr[] of distinct integers and an integer target,
        find all unique combinations of array where the sum of chosen element is equal to target.
        The same element may be chosen any number of times to make target.
    */
    public List<List<Integer>> combinationSum(int[] arr, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(arr); // optional but helps pruning
        backtrack(arr, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] arr, int target, int start,
                           List<Integer> current, List<List<Integer>> result) {

        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < arr.length; i++) {

            // Pruning
            if (arr[i] > target) break;
            current.add(arr[i]);
            // stay at i → reuse allowed
            backtrack(arr, target - arr[i], i, current, result);

            current.remove(current.size() - 1); // backtrack
        }
    }
}