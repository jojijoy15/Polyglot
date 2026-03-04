package com.problems.learning.algo.string;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class UniqueSequence {

    //Find the longest unique substring length from a given String.
    public int uniqueStringLength(String s) {
        char[] characters = s.toCharArray();
        int maxLength = Integer.MIN_VALUE;
        HashSet<Character> unique = new HashSet<>();
        for (int count = 0; count < characters.length; count++) {
            if(unique.add(characters[count])) {
                maxLength = Math.max(maxLength, unique.size());
            } else {
                maxLength = Math.max(maxLength, unique.size());
                unique.clear();
                count--; //This character may be part
            }
        }
        return Math.max(maxLength, 0) ;
    }

    //Find the longest unique substring from a given String.
    public String uniqueString(String s) {
        char[] characters = s.toCharArray();
        HashSet<Character> unique = new HashSet<>();
        int left = 0, right = 0;
        List<Map.Entry<Integer, Integer>> indexTracker = new ArrayList<>();
        for (int count = 0; count < characters.length; count++) {
            if(unique.add(characters[count])) {
                right++;
            } else {
                indexTracker.add(Map.entry(left, right));
                left = right;
                count--;
                unique.clear();
            }
        }
        left = 0; right = 0;
        int maxLength = Integer.MIN_VALUE;
        for(int count = 0; count < indexTracker.size(); count++) {
            Map.Entry<Integer, Integer> entry = indexTracker.get(count);
            int beginning = entry.getKey();
            int end = entry.getValue();
            int subLength = end - beginning;
            maxLength = Math.max(maxLength, subLength);
            if(maxLength == subLength) {
                left = beginning;
                right = end;
            }
        }
        return maxLength == Integer.MIN_VALUE ? "" : s.substring(left, right);
    }
}
