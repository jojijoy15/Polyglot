package com.problems.learning.algo.slidingwindow;

import java.util.Map;

public class LongestRepeatingSubString {

    public Map.Entry<Integer, Integer> findLongestRepeatingCharacter(String sequence) {
        char[] charArray = sequence.toCharArray();
        int left = 0;
        int maxLength = 0;
        int maxLengthBegIndex = 0;
        for(int right = 1; right < charArray.length; right++){
            if(charArray[right] != charArray[right-1] || right == charArray.length-1){
                int length = right - left;
                if(maxLength < length){
                     maxLength = length;
                     maxLengthBegIndex = left;
                }
                left = right;
            }
        }
        return Map.entry(maxLengthBegIndex, maxLength);
    }
}
