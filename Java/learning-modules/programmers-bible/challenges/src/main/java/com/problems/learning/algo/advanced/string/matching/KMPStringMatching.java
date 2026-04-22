package com.problems.learning.algo.advanced.string.matching;

import java.util.*;

public class KMPStringMatching {

    public List<Integer> strStr(String givenWord, String pattern) {
        if(Objects.isNull(pattern)){
            return Collections.emptyList();
        }

        // Set up LPS -> O(2*N)
        int[] lps =  new int[pattern.length()];    // Last prefix sum
        Arrays.fill(lps,0);
        int prevLps = 0; // prefix
        int i = 1;      // suffix
        while(i < pattern.length()) {
            if( pattern.charAt(i) == pattern.charAt(prevLps) ){  //Increment by 1 if both characters match
                lps[i] = prevLps + 1;
                prevLps += 1;
                i += 1;
            } else if (prevLps  == 0) {  // if reached beginning make the lps as 0, no match found
                 lps[i] = 0;
                 i++;
            } else {
                prevLps = lps[prevLps-1]; // if no match then set it as last lps value
            }
        }

        // Setup
        i = 0;     // points to word in which other word need to be found
        int j = 0; // points to word to be found
        List<Integer> indexes = new ArrayList<>();

        //KMP
        //O(N + M) N = length of first word, M = length of second word
        while(i < givenWord.length()) {
            if(givenWord.charAt(i) == pattern.charAt(j)){
                i++;j++;
            }
            /*
            Only one substring
            else {

                if (0 == j) {
                    i++;
                } else {
                    j = lps[j-1];
                }
            }

            if (j == pattern.length()) {
                return i - pattern.length();
            }
            */
            if (j == pattern.length()) { //found the substring, multiple scenario
                indexes.add(i - j);
                j = lps[j - 1];
            } else if (i < givenWord.length() && pattern.charAt(j) != givenWord.charAt(i)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return indexes;
    }

}
