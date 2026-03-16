package com.problems.learning.algo.simple;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DictionaryLookUp {

    /*
        Given a string of letters and a dictionary, the function should find the longest word
        or words in the dictionary that can be made from the letters.
        Only lowercase letters will occur in the dictionary and the letters.
        The length of letters will be between 1 and 10 characters.
        The solution should work well for a dictionary of over 100,000 words. Examples -

            letters = "oet",
            dictionary = {"to","toe","toes"},
            Output: {"toe"}

            letters = "oetdg",
            dictionary = {"to", "toe", "toes", "doe", "dog", "god", "dogs", "book", "banana"},
            Output: {"doe", "toe", "dog", "god"}
     */

    public static Set<String> findLongestWords(String letters, List<String> dictionary) {
        Set<String> result = new LinkedHashSet<>();
        int maxLetters = letters.length();
        int[] letterFreq = buildFreq(letters); // O(1)

        // Sort descending by length → lets us break early
        List<String> sorted = new ArrayList<>(dictionary);
        sorted.sort((a, b) -> b.length() - a.length()); //O(n

        int longestFound = 0;

        for (String word : sorted) {
            int wLen = word.length();
            if (wLen > maxLetters) continue;   // can't build — skip
            if (wLen < longestFound) break;    // nothing longer ahead — stop

            if (isBuildable(word, letterFreq)) {
                longestFound = wLen;
                result.add(word);
            }
        }
        return result;
    }

    private static int[] buildFreq(String letters) {
        int[] freq = new int[26];
        for (int i = 0; i < letters.length(); i++) {
            freq[letters.charAt(i) - 'a']++;
        }
        return freq;
    }

    private static boolean isBuildable(String word, int[] letterFreq) {
        int[] used = new int[26];
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            used[idx]++;
            if (used[idx] > letterFreq[idx]) return false; // fail fast
        }
        return true;
    }
}
