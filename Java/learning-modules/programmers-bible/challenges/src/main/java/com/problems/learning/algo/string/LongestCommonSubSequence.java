package com.problems.learning.algo.string;

import java.util.*;

public class LongestCommonSubSequence {

    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();

        int[][] table = new int[len1 + 1][len2 + 1]; //extra space to handle edge condition
        //Bottom Up
        for (int i = len1 - 1 ; i > -1; i--) {
            for (int j = len2 - 1 ; j > -1; j--) {
                if(text1.charAt(i) == text2.charAt(j)) {
                    table[i][j] = 1 +  table[i + 1][j + 1];
                } else {
                    table[i][j] = Math.max(table[i + 1][j], table[i][j + 1]); //
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

        List<Integer> indices = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < len1 && j < len2) {
            if (text1.charAt(i) == text2.charAt(j)) {
                indices.add(i);
                i++;
                j++;
            } else if (table[i + 1][j] > table[i][j + 1]) {
                i++;
            } else {
                j++;
            }
        }
        return Map.entry(table[0][0], indices);
    }

}
