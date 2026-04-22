package com.problems.learning.algo.dp.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LongestCommonSubSequence {


    /*
           str1 : abcde
           str2 : ace

              a  c  e  X
           a  3  2  1  0
           b  2  2  1  0
           c  2  2  1  0
           d  1  1  1  0
           e  1  1  1  0
           X  0  0  0  0
     */

    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();

        int[][] table = new int[len1 + 1][len2 + 1]; //extra space to handle edge condition
        //Bottom Up
        for (int i = len1 - 1 ; i > -1; i--) {
            for (int j = len2 - 1 ; j > -1; j--) {
                if(text1.charAt(i) == text2.charAt(j)) {
                    table[i][j] = 1 + table[i + 1][j + 1];
                } else {
                    table[i][j] = Math.max(table[i + 1][j], table[i][j + 1]);
                }
            }
        }

        Arrays.stream(table)
                .forEach(row -> System.out.println(Arrays.toString(row)));
        return table[0][0];
    }

    public Map.Entry<Integer, List<Integer>> longestCommonSubsequenceWithIndex(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();

        int[][] table = new int[len1 + 1][len2 + 1]; //extra space to handle edge condition
        //Bottom Up
        for (int i = len1 - 1 ; i > -1; i--) {
            for (int j = len2 - 1 ; j > -1; j--) {
                if(text1.charAt(i) == text2.charAt(j)) {
                    table[i][j] = 1 +  table[i + 1][j + 1];
                } else {
                    table[i][j] = Math.max(table[i + 1][j], table[i][j + 1]);
                }
            }
        }

        /*
           str1 : abcde
           str2 : ace

              a  c  e  X
           a  3  2  1  0
           b  2  2  1  0
           c  2  2  1  0
           d  1  1  1  0
           e  1  1  1  0
           X  0  0  0  0
        */
        List<Integer> indices = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < len1 && j < len2) {
            if (text1.charAt(i) == text2.charAt(j)) {
                indices.add(i);
                i++;
                j++;
            } else if (table[i + 1][j] > table[i][j + 1]) { // Go to where you can find max sub-sequence
                i++;
            } else {
                j++;
            }
        }
        return Map.entry(table[0][0], indices);
    }

    // Space Optimized: O(min(len1, len2)) using two 1D arrays
    public int longestCommonSubsequenceOptimized(String text1, String text2) {
        // Ensure text2 is the shorter string to minimize space
        if (text1.length() < text2.length()) {
            String temp = text1; text1 = text2; text2 = temp;
        }
        int len1 = text1.length();
        int len2 = text2.length();

        int[] prev = new int[len2 + 1]; // represents row i+1
        int[] curr = new int[len2 + 1]; // represents row i

        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    curr[j] = 1 + prev[j + 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j + 1]);
                }
            }
            // swap
            int[] temp = prev;
            prev = curr;
            curr = temp;
            Arrays.fill(curr, 0);
        }
        return prev[0];
    }
}
