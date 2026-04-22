package com.problems.learning.algo.backtracking.permutation;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

    public int[][] permutation(int[] numbers) {

        List<List<Integer>> allPermutations = permute(numbers, 0);
        return allPermutations.stream()
                .map( e -> {
                    int size = e.size();
                    int[] row =  new int[size];
                    for (int i = 0; i < size; i++) {
                        row[i] = e.get(i);
                    }
                    return row;
                })
                .toArray(int[][]::new);

    }

    //Space: O(n!), Time : O(n ^ 2 x n!
    private List<List<Integer>> permute(int[] numbers, int index) {
        // Return an empty list of list
        if (index == numbers.length) {
            List<List<Integer>> container = new ArrayList<>();
            container.add(new ArrayList<>());
            return container;
        }
        //Note: Empty list for each permutations
        List<List<Integer>> permutations = new ArrayList<>();

        //Note : Call recursively until you reach last element
        List<List<Integer>> container = permute(numbers, index + 1);
        for (List<Integer> permute : container) {
            for (int i = 0; i < permute.size() + 1; i++) {
                //Note:  Create multiple copy of each permute
                List<Integer> newList = new ArrayList<>(permute);
                /* Note:
                    Will go reverse from last Index to one
                    Insert the indexed element at each permute index
                 */
                newList.add(i, numbers[index]);
                permutations.add(newList);
            }
        }
        return permutations;
    }

    public int[][] permutationIteratively(int[] numbers) {

        List<List<Integer>> allPermutations = permuteIteratively(numbers);
        return allPermutations.stream()
                .map( e -> {
                    int size = e.size();
                    int[] row =  new int[size];
                    for (int i = 0; i < size; i++) {
                        row[i] = e.get(i);
                    }
                    return row;
                })
                .toArray(int[][]::new);
    }

    //Space: O(n!), Time : O(n ^ 2 x n!)
    private List<List<Integer>> permuteIteratively(int[] numbers) {

        List<List<Integer>> permutations = new ArrayList<>();
        permutations.add(new ArrayList<>());

        for (Integer number : numbers) {
            List<List<Integer>> nextPerm = new ArrayList<>();
            for (List<Integer> permute : permutations) {
                for (int i = 0; i < permute.size() + 1; i++) {
                    //Note: Create multiple copy for each permute
                    List<Integer> newList = new ArrayList<>(permute);
                    newList.add(i, number);
                    nextPerm.add(newList);
                }
            }
            permutations = nextPerm;
        }
        return permutations;
    }
}
