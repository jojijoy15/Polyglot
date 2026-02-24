package recursion.sorting;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import recursion.sorting.MergeSort;

class MergeSortTest {

  private MergeSort mergeSort = new MergeSort();

  @Test
  void sort() {
    int[] integers = {10, 34, 2, 98, 12, 42, 21, 53};
    mergeSort.sort(integers);
    System.out.println(Arrays.toString(integers));
  }
}