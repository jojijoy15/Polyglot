package com.problems.learning.algo.twopointer;

public class MergeElements {

    //Given two sorted arrays, merge them to single sorted array.
    public int[] mergeSorted(int[] first, int[] second) {
        int firstLength = first.length;
        int secondLength = second.length;
        int totalLength = firstLength + secondLength;
        int[] merged = new int[totalLength];
        int i = 0, j = 0, k = 0;
        while (i < firstLength && j < secondLength) {
            if(first[i] <= second[j]) {
                merged[k++] = first[i++];
            } else {
                merged[k++] = second[j++];
            }
        }
        while (i < firstLength) {
            merged[k++] = first[i++];
        }
        while (j < secondLength) {
            merged[k++] = second[j++];
        }
        return merged;
    }

    // Given Two sorted arrays and merge them into single sorted array without using extra space.
    // Assume first array will contain the space
    public int[] mergeSortedContained(int[] first, int end, int[] second) {
        int lastIndex = first.length - 1;
        int reverseIndexOfFirst = end; // 0-based
        int reverseIndexOfSecond = second.length - 1;

        while(reverseIndexOfFirst >=0 && reverseIndexOfSecond >= 0){
            if(first[reverseIndexOfFirst] > second[reverseIndexOfSecond]) {
                first[lastIndex] = first[reverseIndexOfFirst];
                lastIndex--;
                reverseIndexOfFirst--;
            } else {
                first[lastIndex] = second[reverseIndexOfSecond];
                lastIndex--;
                reverseIndexOfSecond--;
            }
        }
        while(reverseIndexOfFirst >= 0) {
            first[lastIndex] = first[reverseIndexOfFirst];
            lastIndex--;
            reverseIndexOfFirst--;
        }

        while(reverseIndexOfSecond >= 0) {
            first[lastIndex] = second[reverseIndexOfSecond];
            lastIndex--;
            reverseIndexOfSecond--;
        }
        return first;
    }
}
