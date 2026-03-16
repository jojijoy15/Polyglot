package com.problems.learning.algo.string;

import java.util.*;

public class UniqueSequence {

    //Find the longest unique substring length from a given String.
    public int uniqueStringLength(String s) {
        char[] characters = s.toCharArray();
        int maxLength = Integer.MIN_VALUE;
        HashSet<Character> unique = new HashSet<>();
        for (int count = 0; count < characters.length; count++) {
            if(unique.add(characters[count])) {
                maxLength = Math.max(maxLength, unique.size());
            } else {
                maxLength = Math.max(maxLength, unique.size());
                unique.clear();
                unique.add(characters[count]);
            }
        }
        return Math.max(maxLength, 0) ;
    }

    //Find the longest unique substring from a given String.
    public String uniqueString(String s) {
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
