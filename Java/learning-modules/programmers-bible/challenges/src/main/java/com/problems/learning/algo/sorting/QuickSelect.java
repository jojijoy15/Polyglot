package com.problems.learning.algo.sorting;

import java.util.Random;

public class QuickSelect {

    public int kthGreatestElement(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int partition = partition(arr, low, high);
            if (partition < k - 1) {
                low = partition + 1;
            } else {
                high = partition - 1;
            }
        }
        return arr[low];
    }

    //Uses Lumoto's partition
    private int partition(int[] arr, int low, int high) {
        int randomPivotIndex = new Random().nextInt(low, high + 1);
        swap(arr, randomPivotIndex, high);
        int j = low;
        for (int i = low; i <= high; i++) {
            if (arr[i] > arr[high]) {
                swap(arr, i, j);
                j += 1;
            }
        }
        swap(arr, high, j);//swap the pivot at correct place
        return j;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
