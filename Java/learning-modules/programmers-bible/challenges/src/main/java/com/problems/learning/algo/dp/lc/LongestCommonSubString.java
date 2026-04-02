package com.problems.learning.algo.dp.lc;

/**
 * Longest Common Substring (contiguous characters).
 *
 * Key difference from Subsequence:
 *   - Subsequence: mismatch → max(table[i+1][j], table[i][j+1])
 *   - Substring:   mismatch → 0 (contiguity broken, reset)
 *
 * Example:
 *   s1 = "abcde", s2 = "abfce"
 *
 *           a  b  f  c  e  X
 *        a  2  0  0  0  0  0
 *        b  0  1  0  0  0  0
 *        c  0  0  0  1  0  0
 *        d  0  0  0  0  0  0
 *        e  0  0  0  0  1  0
 *        X  0  0  0  0  0  0
 *
 *   Longest common substring = "ab" (length 2)
 *
 * Time: O(m * n), Space: O(m * n)
 */
public class LongestCommonSubString {

    public int longestCommonSubstring(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        int[][] table = new int[len1 + 1][len2 + 1];
        int maxLength = 0;

        // Bottom Up
        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    table[i][j] = 1 + table[i + 1][j + 1];
                    maxLength = Math.max(maxLength, table[i][j]);
                }
                // else table[i][j] remains 0 — contiguity broken
            }
        }

        return maxLength;
    }

    public String longestCommonSubstringValue(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        int[][] table = new int[len1 + 1][len2 + 1];
        int maxLength = 0;
        int endIndex = 0; // tracks where the longest substring ends in s1

        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    table[i][j] = 1 + table[i + 1][j + 1];
                    if (table[i][j] > maxLength) {
                        maxLength = table[i][j];
                        endIndex = i; // substring starts at i in s1
                    }
                }
            }
        }

        return s1.substring(endIndex, endIndex + maxLength);
    }
}

