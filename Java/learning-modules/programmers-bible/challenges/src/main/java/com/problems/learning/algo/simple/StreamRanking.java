package com.problems.learning.algo.simple;

import com.problems.learning.tags.Medium;

import java.util.TreeMap;

@Medium
public class StreamRanking {

    /*
     * Given a stream of integers, as each new integer arrives, output its rank.
     * Rank 1 = highest value. Duplicates are placed after existing ones (later = lower rank).
     *
     * Input:  15  20  36  45  25  36  25  80  36
     * Output:  1   1   1   1   3   3   5   1   5
     *
     * Approach: TreeMap<value, count> (descending order)
     *   For each new value:
     *     rank = (count of elements strictly greater) + (count of equal elements already present) + 1
     *
     *   TreeMap.tailMap(value, false) → all entries with key > value (since map is reversed)
     *     Wait — reversed map: tailMap gives keys "after" in iteration order, which is < value.
     *     So use headMap(value, false) on a reversed TreeMap to get keys > value.
     *
     *   Simpler: use natural order TreeMap.
     *     - Keys > value: sum counts from tailMap(value, false)
     *     - Equal already present: map.getOrDefault(value, 0)
     *     - Rank = countGreater + countEqual + 1
     *
     * Time per element: O(n) worst case for summing tail counts
     *   (Could be O(log n) with a Fenwick/BIT tree, but TreeMap is clearer for learning)
     * Space: O(distinct values)
     */

    private final TreeMap<Integer, Integer> countMap = new TreeMap<>();
    private int totalElements = 0;

    public int addAndRank(int value) {
        // Count of elements strictly greater than value
        int countGreater = countMap.tailMap(value, false)
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        // Count of equal elements already in the stream
        int countEqual = countMap.getOrDefault(value, 0);

        //rank = countGreater + 1          // dense ranking
        // Rank = greater + equal (placed after) + 1
        int rank = countGreater + countEqual + 1;

        // Add to map
        countMap.merge(value, 1, Integer::sum);
        totalElements++;

        return rank;
    }

    /**
     * Process an entire stream and return ranks for each element.
     *
     * Input:  [15, 20, 36, 45, 25, 36, 25, 80, 36]
     * Output: [1,   1,  1,  1,  3,  3,  5,  1,  5]
     */
    public int[] rankStream(int[] stream) {
        int[] ranks = new int[stream.length];
        for (int i = 0; i < stream.length; i++) {
            ranks[i] = addAndRank(stream[i]);
        }
        return ranks;
    }

    public void reset() {
        countMap.clear();
        totalElements = 0;
    }
}

