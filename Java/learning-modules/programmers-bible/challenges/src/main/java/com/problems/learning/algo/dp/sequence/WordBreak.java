package com.problems.learning.algo.dp.sequence;

import java.util.Arrays;

public class WordBreak {

    public Boolean matchWordsInDictionary(String s, String[] wordDict) {

        boolean[] dp = new boolean[s.length() + 1];
        dp[s.length()] = true; // Empty string is a valid break
        for (int i = s.length() - 1; i >= 0; i--) {
            for (String word : wordDict) {
                int wlen = word.length();
                if((i + wlen <= s.length()) && (word.equals(s.substring(i, i + wlen)))) {
                    dp[i] = dp[i + wlen];
                }
                if(dp[i]) {
                    break;
                }
            }
        }
        return dp[0];
    }
}