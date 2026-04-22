package com.problems.learning.algo.slidingwindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UniqueSequence {

    //Find the longest unique substring length from a given String.
    public int longestUniqueStringLength(String s) {
        char[] characters = s.toCharArray();
        int maxLength = Integer.MIN_VALUE;
        int left = 0;
        HashSet<Character> unique = new HashSet<>();
        for (int right = 0; right < characters.length; right++) {
            while (!unique.add(characters[right])) {
                unique.remove(characters[left]);
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

            /*
             * Note:
             * I/p: abcdabbefhgf
             *
             * l = 0, r = 0  | {'a': 0}
             * l = 0, r = 1  | {'a': 0, 'b': 1}
             * l = 0, r = 2  | {'a': 0, 'b': 1, 'c' : 2}
             * l = 0, r = 3  | {'a': 0, 'b': 1, 'c' : 2, 'd' : 3}
             * l = 1, r = 4  | {'a': 4, 'b': 1, 'c' : 2, 'd' : 3}
             */
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

    public String longestSubstringAtMostKDistinct(String s, int k) {
        if (s == null || s.isEmpty() || k == 0)
            return "";

        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int maxLen = 0;
        int maxStart = 0;

        for (int right = 0; right < s.length(); right++) {
            // Expand window: add right character
            char rightChar = s.charAt(right);
            charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);

            // Shrink window from left if distinct chars exceed k
            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                left++;
            }

            // Update max length & start
            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                maxStart = left;
            }
        }

        return s.substring(maxStart, maxStart + maxLen);
    }

    // Exactly K distinct = atMostK(k) - atMostK(k-1)
    public String longestSubstringExactlyKDistinct(String s, int k) {
        if (s == null || s.isEmpty() || k == 0) return "";

        int maxLen = 0, maxStart = 0;
        /*
         * Early optimization
        int atMostKLen = longestSubstringKDistinctLength(s, k);
        int atMostKMinus1Len = longestSubstringKDistinctLength(s, k - 1);

        // If atMostK == atMostK-1, no window with exactly K distinct exists
        if (atMostKLen == atMostKMinus1Len) return "";
        */
        // Find the actual substring with exactly K distinct
        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            charCount.merge(s.charAt(right), 1, Integer::sum);

            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                if (charCount.merge(leftChar, -1, Integer::sum) == 0)
                    charCount.remove(leftChar);
                left++;
            }

            if (charCount.size() == k && right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                maxStart = left;
            }
        }
        return s.substring(maxStart, maxStart + maxLen);
    }

    private int longestSubstringKDistinctLength(String s, int k) {
        if (k == 0) return 0;
        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0, maxLen = 0;
        for (int right = 0; right < s.length(); right++) {
            charCount.merge(s.charAt(right), 1, Integer::sum);
            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                if (charCount.merge(leftChar, -1, Integer::sum) == 0)
                    charCount.remove(leftChar);
                left++;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

}
