package com.problems.learning.algo.slidingwindow;

import com.problems.learning.tags.Medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Medium
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

    /*
     * Minimum length substring with at least K distinct characters.
     *
     * Approach: Sliding window — expand right to get K distinct, then shrink left to minimize.
     *
     * Example: s = "aabcbcdbca", k = 2
     *   Window "aab" has 2 distinct, length 3
     *   Shrink → "ab" has 2 distinct, length 2 → min so far
     *
     * Time: O(n), Space: O(k)
     */
    public int minSubstringLengthWithKDistinct(String s, int k) {
        if (s == null || s.isEmpty() || k == 0) return 0;
        if (k > s.length()) return -1;

        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            charCount.merge(s.charAt(right), 1, Integer::sum);

            // Once we have at least K distinct, shrink from left
            while (charCount.size() >= k) {
                minLen = Math.min(minLen, right - left + 1);
                char leftChar = s.charAt(left);
                if (charCount.merge(leftChar, -1, Integer::sum) == 0) {
                    charCount.remove(leftChar);
                }
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }

    /*
     * Find the subarray of size k with the most unique (distinct) elements.
     * Returns the starting index of that subarray.
     *
     * Approach: Fixed-size sliding window of size k with a frequency map.
     *   - Slide the window: add right element, remove left element
     *   - Track distinct count via map size
     *   - Early exit if distinct == k (all unique, can't do better)
     *
     * Example: arr = [1, 2, 1, 3, 4, 2, 3], k = 4
     *   [1,2,1,3] → 3 distinct
     *   [2,1,3,4] → 4 distinct ← best (early exit)
     *   Answer: index 1
     *
     * Time: O(n), Space: O(k)
     */
    public int maxUniqueSubarray(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k > arr.length || k <= 0) return -1;

        Map<Integer, Integer> freq = new HashMap<>();
        int maxDistinct = 0;
        int bestStart = 0;

        // Build initial window of size k
        for (int i = 0; i < k; i++) {
            freq.merge(arr[i], 1, Integer::sum);
        }
        maxDistinct = freq.size();

        // Slide the window
        for (int right = k; right < arr.length; right++) {
            if (maxDistinct == k) break; // all unique — can't do better

            // Add new right element
            freq.merge(arr[right], 1, Integer::sum);

            // Remove leftmost element of previous window
            int left = right - k;
            if (freq.merge(arr[left], -1, Integer::sum) == 0) {
                freq.remove(arr[left]);
            }

            if (freq.size() > maxDistinct) {
                maxDistinct = freq.size();
                bestStart = left + 1;
            }
        }

        return bestStart;
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
