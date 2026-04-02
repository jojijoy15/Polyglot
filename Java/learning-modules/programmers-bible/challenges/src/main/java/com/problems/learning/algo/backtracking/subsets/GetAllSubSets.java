package com.problems.learning.algo.backtracking.subsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetAllSubSets {

    public Integer[][] subSets(int[] nums) {

        Arrays.sort(nums); // Sort to avoid duplicates
        List<List<Integer>> containers = new ArrayList<>();
        createSubsets(nums, 0, new ArrayList<>(), containers);
        return containers.stream()
                .map(e -> e.toArray(Integer[]::new))
                .toArray(Integer[][]::new);
    }

    private void createSubsets(int[] nums, int index, List<Integer> current, List<List<Integer>> containers) {
        if(index >= nums.length) {
            containers.add(new ArrayList<>(current));
            return;
        }
        current.add(nums[index]);
        createSubsets(nums, index + 1, current, containers);
        current.remove(current.size() - 1);
        while ((index + 1 ) < nums.length &&  (nums[index + 1] == nums[index])) {
            index++;
        }
        createSubsets(nums, index + 1, current, containers);
    }

    private void createSubsetsLoop(int index, int[] elements, List<Integer> subSet, List<List<Integer>> container) {
        container.add(new ArrayList<>(subSet));
        for(int i = index; i < elements.length; i++) {
            subSet.add(elements[i]);
            createSubsetsLoop(i + 1, elements, subSet, container);
            subSet.remove(subSet.size() - 1); // Need to remove the last element to create new subset without that element
        }
    }
}
