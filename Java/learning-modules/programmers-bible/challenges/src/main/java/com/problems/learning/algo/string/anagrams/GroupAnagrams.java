package com.problems.learning.algo.string.anagrams;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams {


    // Approach 2 — Frequency array as key  O(n * w) — faster for long words
    public List<List<String>> groupAnagramsFreq(String[] words) {
        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (String word : words) {
            String key = buildFreqKey(word);                  // "pan" → "a1n1p1"
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    private String buildFreqKey(String letters) {
        int[] freq = new int[26];
        for (int i = 0; i < letters.length(); i++) {
            freq[letters.charAt(i) - 'a']++;
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < freq.length; i++){
            if(freq[i] > 0) {
                sb.append('a' + i);
                sb.append(freq[i]);
            }
        }
        return sb.toString();
    }
}
