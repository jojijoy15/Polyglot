package com.problems.learning.algo.sorting.sequence;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

class MergeSortTest {

  private MergeSort mergeSort = new MergeSort();

  @Test
  void sort() {
    int[] integers = {10, 34, 2, 98, 12, 42, 21, 53};
    mergeSort.sort(integers);
    System.out.println(Arrays.toString(integers));
  }
}