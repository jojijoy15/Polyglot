package com.problems.learning.algo.backtracking.combination;

import java.util.ArrayList;
import java.util.List;

public class GetAllCombinations {

    /*
        nCk = C(n,k) = n!/k!*(n-k)!
     */
    public Integer[][] combinations(int[] nums, int k) {
        List<List<Integer>> containers = new ArrayList<>();
        createSubsets(0, nums, new ArrayList<>(), containers, k);
        return containers.stream()
                .map(e -> e.toArray(Integer[]::new))
                .toArray(Integer[][]::new);
    }

    //TODO: Time Complexity: k * C(n-k) :
    private void createSubsets(int index, int[] elements, List<Integer> combo, List<List<Integer>> container, int k) {
        if(combo.size() >= k) {
            container.add(new ArrayList<>(combo));
            return;
        }
        if(index > elements.length) {
            return;
        }
        for(int i = index; i < elements.length ; i++) {
            combo.add(elements[i]);
            createSubsets(i + 1, elements, combo, container, k);
            combo.remove(combo.size() - 1); // Need to remove the last element to create new subset without that element
        }
    }
}
