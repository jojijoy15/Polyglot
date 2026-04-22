package com.problems.learning.algo.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianOfStream {

    /*
      Will remove smaller elements first
           For Larger Elements
       */
    PriorityQueue<Integer> largeMinHeap = new PriorityQueue<>();
    /*
        Will remove larger elements first
        For smaller Elements
    */
    PriorityQueue<Integer> smallMaxHeap = new PriorityQueue<>(Comparator.reverseOrder());

    public void addToStream(int element) {
        smallMaxHeap.add(element);

        if((null != largeMinHeap.peek() && null != smallMaxHeap.peek())
                && (smallMaxHeap.peek() > largeMinHeap.peek())
        ) {
            largeMinHeap.offer(smallMaxHeap.poll());
        }

        if(largeMinHeap.size() > smallMaxHeap.size()  + 1 ) {
            smallMaxHeap.offer(largeMinHeap.poll());
        }
        if (smallMaxHeap.size() > largeMinHeap.size() + 1 ) {
            largeMinHeap.offer(smallMaxHeap.poll());
        }
    }

    public double findMedian() {
        if(smallMaxHeap.size() > largeMinHeap.size()) {
            return smallMaxHeap.peek();
        }
        if (largeMinHeap.size() > smallMaxHeap.size()) {
            return largeMinHeap.peek();
        }
        return (largeMinHeap.peek() +  smallMaxHeap.peek()) / 2.0;
    }


}
