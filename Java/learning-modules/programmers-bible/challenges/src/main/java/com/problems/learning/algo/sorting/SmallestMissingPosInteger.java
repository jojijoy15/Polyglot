package com.problems.learning.algo.sorting;

import java.util.TreeSet;

public class SmallestMissingPosInteger {

    public int findMissingNumber(int[] numbers) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int number : numbers) {
            set.add(number);
        }
        int missingNumber = 1;
        for (int i = 1; i < numbers.length; i++) {
            if (!set.contains(i)) {
                return missingNumber;
            }
            missingNumber++;
        }
        return missingNumber;
    }


    public int findMissingNumberOptimised(int[] numbers) {
        int n = numbers.length;
        for (int i = 0; i < n; i++) {
            while (numbers[i] > 0 && numbers[i] <= n && numbers[i] != numbers[numbers[i] - 1]) {
                int correctIndex = numbers[i] - 1;
                int temp = numbers[i];
                numbers[i] = numbers[correctIndex];
                numbers[correctIndex] = temp;
            }
        }

        // First index where numbers[i] != i+1 is the answer
        int i;
        for (i = 0; i < n; i++) {
            if (numbers[i] != i + 1) {
                return i + 1;
            }
        }
        return i + 1;
    }

}
