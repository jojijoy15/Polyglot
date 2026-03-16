package com.problems.learning.algo.string.subsequence;

import java.util.Arrays;

public class LongestRepeatingSubSequence {

    public int longestRepeatingSubsequence(String word) {
        int len = word.length();

        int[][] table = new int[len + 1][len + 1]; //extra space to handle edge condition
        //Bottom Up
        for (int i = len - 1 ; i > -1; i--) {
            for (int j = len - 1 ; j > -1; j--) {
                if( (i != j) && (word.charAt(i) == word.charAt(j))) {
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

}
