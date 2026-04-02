package com.problems.learning.algo.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a string, split it into substrings such that no two substrings
 * share the same character.
 *
 * Example:
 *   Input:  "ababbacaadefgegdehjikjljkl"
 *   Output: ["ababbacaa", "defgegde", "hjikjljkl"]
 *
 * Approach:
 *   1. Record the last index of every character in the string.
 *   2. Walk through the string keeping track of the farthest last-index
 *      of any character seen in the current partition.
 *   3. When the current index reaches that farthest point, close the
 *      partition and start a new one.
 *
 * Time:  O(n)
 * Space: O(1) — at most 26 entries in the map
 */
public class PartitionLabels {

    public List<String> partition(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.isEmpty()) {
            return result;
        }

        // Step 1 – record last occurrence of each character
        Map<Character, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            lastIndex.put(s.charAt(i), i);
        }

        // Step 2 – greedily find partitions
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, lastIndex.get(s.charAt(i)));

            // Step 3 – current index has reached the farthest point
            if (i == end) {
                result.add(s.substring(start, end + 1));
                start = end + 1;
            }
        }

        return result;
    }
}

