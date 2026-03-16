package recursion.sorting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    InsertionSort sort = new InsertionSort();

    @Test
    void sortOddLength() {
        int[] elements = new int[] {10, 24, 4, 24, 11, 5, 19};
        sort.sort(elements);
        assertThat(elements).containsExactly(4, 5, 10, 11, 19, 24, 24);
    }

    @Test
    void sortEvenLength() {
        int[] elements = new int[] {10, 24, -1, 4, 24, 11, 5, 19};
        sort.sort(elements);
        assertThat(elements).containsExactly(-1, 4, 5, 10, 11, 19, 24, 24);
    }
}