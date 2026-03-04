package recursion.sorting;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    HeapSort sort = new HeapSort();

    @Test
    void sort() {
        int[] elements = { 11, 2, 35, 14, 59, 16, 7, 81, 19 };
        sort.sort(elements);
        assertThat(elements).containsExactly( 2, 7, 11, 14, 16, 19, 35, 59, 81);
    }
}