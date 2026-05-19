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

    /**
     * Follow-up 1: All integers are in the range [0, 100].
     *
     * Optimization: Use a counting/bucket array of size 101.
     * - addToStream: O(1)
     * - findMedian: O(101) = O(1) since range is fixed
     * - Space: O(101) = O(1)
     */
    static class MedianOfStreamBounded {
        int[] count = new int[101]; // count[i] = frequency of value i
        int totalCount = 0;

        public void addToStream(int element) {
            count[element]++;
            totalCount++;
        }

        public double findMedian() {
            // Find the median by walking through the counts
            if (totalCount == 0) throw new IllegalStateException("No elements");

            int mid1 = (totalCount + 1) / 2; // position of first middle element (1-indexed)
            int mid2 = (totalCount + 2) / 2; // position of second middle element (same as mid1 if odd)

            int cumulative = 0;
            int val1 = -1, val2 = -1;

            for (int i = 0; i <= 100; i++) {
                cumulative += count[i];
                if (val1 == -1 && cumulative >= mid1) {
                    val1 = i;
                }
                if (val2 == -1 && cumulative >= mid2) {
                    val2 = i;
                    break;
                }
            }

            return (val1 + val2) / 2.0;
        }
    }

    /**
     * Follow-up 2: 99% of integers are in [0, 100], but some can be outside.
     *
     * Optimization: Use counting array for [0, 100].
     * Track count of numbers < 0 (belowCount) and > 100 (aboveCount).
     * Use two heaps only for the outlier values (rare, ~1% of data).
     *
     * - If median position falls in belowCount range → get from belowHeap (max-heap)
     * - If median position falls in [0,100] range → walk the count array
     * - If median position falls in aboveCount range → get from aboveHeap (min-heap)
     */
    static class MedianOfStreamMostlyBounded {
        int[] count = new int[101];
        int totalCount = 0;
        int belowCount = 0; // how many elements < 0
        int aboveCount = 0; // how many elements > 100

        // Max-heap for elements < 0 (peek gives largest of the below-range elements)
        PriorityQueue<Integer> belowHeap = new PriorityQueue<>(Comparator.reverseOrder());
        // Min-heap for elements > 100 (peek gives smallest of the above-range elements)
        PriorityQueue<Integer> aboveHeap = new PriorityQueue<>();

        public void addToStream(int element) {
            if (element < 0) {
                belowHeap.offer(element);
                belowCount++;
            } else if (element > 100) {
                aboveHeap.offer(element);
                aboveCount++;
            } else {
                count[element]++;
            }
            totalCount++;
        }

        public double findMedian() {
            if (totalCount == 0) throw new IllegalStateException("No elements");

            int mid1 = (totalCount + 1) / 2;
            int mid2 = (totalCount + 2) / 2;

            return (getValueAtPosition(mid1) + getValueAtPosition(mid2)) / 2.0;
        }

        private int getValueAtPosition(int pos) {
            // Position falls in the below-range elements
            if (pos <= belowCount) {
                return getNthFromHeap(belowHeap, pos, true);
            }

            // Position falls in the [0, 100] range
            int cumulative = belowCount;
            for (int i = 0; i <= 100; i++) {
                cumulative += count[i];
                if (cumulative >= pos) {
                    return i;
                }
            }

            // Position falls in the above-range elements
            int abovePos = pos - belowCount - (totalCount - belowCount - aboveCount);
            return getNthFromHeap(aboveHeap, abovePos, false);
        }

        /**
         * Gets the Nth element from a heap without destroying it.
         * For belowHeap (max-heap): 1st = smallest, so we need ascending order → sort
         * For aboveHeap (min-heap): 1st = smallest, position maps directly
         */
        private int getNthFromHeap(PriorityQueue<Integer> heap, int n, boolean reverseOrder) {
            // Convert heap to sorted array to pick nth element
            int[] sorted = heap.stream().mapToInt(Integer::intValue).sorted().toArray();
            if (reverseOrder) {
                // belowHeap: position 1 = smallest element = sorted[0]
                return sorted[n - 1];
            }
            return sorted[n - 1];
        }
    }
}
