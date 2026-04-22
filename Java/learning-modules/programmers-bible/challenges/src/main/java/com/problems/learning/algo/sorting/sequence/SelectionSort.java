package com.problems.learning.algo.sorting.sequence;

import com.problems.learning.algo.sorting.Sort;

public class SelectionSort implements Sort {


    @Override
    public int[] sort(int[] elements) {
        for (int i = 0; i < elements.length; i++) {
            int min = i;
            for (int j = i + 1; j < elements.length; j++) {
                if (elements[j] < elements[min]) {
                    min = j;
                }
            }
            int temp = elements[i];
            elements[i] = elements[min];
            elements[min] = temp;
        }
        return elements;
    }
}
