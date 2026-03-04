package com.problems.learning.algo.string;

import java.util.Map;

public class LongestRepeatingSubString {

    public Map.Entry<Integer, Integer> findLongestRepeatingCharacter(String sequence) {
        char[] charArray = sequence.toCharArray();
        int left = 0;
        int maxLength = 0;
        int maxLengthBegIndex = 0;
        for(int i = 1; i < charArray.length; i++){
            if(charArray[i] != charArray[i-1] || i == charArray.length-1){
                int length = i - left;
                if(maxLength < length){
                     maxLength = length;
                     maxLengthBegIndex = left;
                }
                left = i;
            }
        }
        return Map.entry(maxLengthBegIndex, maxLength);
    }
}
