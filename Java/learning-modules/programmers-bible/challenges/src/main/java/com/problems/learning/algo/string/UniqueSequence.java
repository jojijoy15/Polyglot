package com.problems.learning.algo.string;

import java.util.*;

public class UniqueSequence {

    //Find the longest unique substring length from a given String.
    public int longestUniqueStringLength(String s) {
        char[] characters = s.toCharArray();
        int maxLength = Integer.MIN_VALUE;
        int left = 0;
        HashSet<Character> unique = new HashSet<>();
        for (int right = 0; right < characters.length; right++) {
            while (!unique.add(s.charAt(right))) {
                unique.remove(s.charAt(left));
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength == Integer.MIN_VALUE ? 0 : maxLength;
    }

    //Find the longest unique substring from a given String.
    public String longestUniqueString(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int left = 0, maxStart = 0, maxLength = 0;
        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);

            if (lastSeen.containsKey(current) && lastSeen.get(current) >= left) {
                left = lastSeen.get(current) + 1;
            }

            lastSeen.put(current, right);

            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1;
                maxStart = left;
            }
        }
        return s.substring(maxStart, maxStart + maxLength);
    }
}
