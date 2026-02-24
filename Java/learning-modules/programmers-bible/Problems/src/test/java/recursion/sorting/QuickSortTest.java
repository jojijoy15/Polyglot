package recursion.sorting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import recursion.sorting.QuickSort;

class QuickSortTest {

  private QuickSort qSort = new QuickSort();

  @Test
  void sort() {
    int[] elements = new int[]{1, 12, 13, 2, 10, 3};
    qSort.sort(elements);
    int[] expected = Arrays.stream(elements).sorted().toArray();
    assertThat(elements).isEqualTo(expected);
  }
}