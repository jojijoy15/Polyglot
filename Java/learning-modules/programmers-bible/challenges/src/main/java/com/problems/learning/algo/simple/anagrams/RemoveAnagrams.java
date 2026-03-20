package com.problems.learning.algo.simple.anagrams;

import java.util.*;

public class RemoveAnagrams {

    public String[] removeAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> nonAnagramWords = new ArrayList<>();
        for(String str : strs) {
            String anagramKey = buildAnagramKey(str);
            map.computeIfAbsent(anagramKey, e -> new ArrayList<>()).add(str);
        }
        for(Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> list = entry.getValue();
            if (!list.isEmpty()) {
                nonAnagramWords.add(list.get(0));
            }
        }
        nonAnagramWords.sort(Comparator.naturalOrder());
        return nonAnagramWords.toArray(String[]::new);
    }

    private String buildAnagramKey(String s) {
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < freq.length; j++) {
            if(freq[j] != 0) {
                sb.append((char)(j + 'a'));
                sb.append(freq[j]);
            }
        }
        return sb.toString();
    }
}
