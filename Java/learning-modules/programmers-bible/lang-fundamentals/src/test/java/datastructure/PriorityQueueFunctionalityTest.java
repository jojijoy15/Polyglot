package datastructure;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

public class PriorityQueueFunctionalityTest {

    @Test
    public void minHeapTest() {
        //Arranges in ascending order with smallest value first

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.add(2);
        minHeap.add(1);
        minHeap.add(3);
        assertThat(minHeap.peek()).isEqualTo(1);
        assertThat(minHeap.poll()).isEqualTo(1);
    }

    @Test
    public void maxHeapTest() {
        //Arranges in descending order with largest value first
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.add(1);
        maxHeap.add(3);
        maxHeap.add(2);

        assertThat(maxHeap.peek()).isEqualTo(3);
        assertThat(maxHeap.poll()).isEqualTo(3);

    }
}
