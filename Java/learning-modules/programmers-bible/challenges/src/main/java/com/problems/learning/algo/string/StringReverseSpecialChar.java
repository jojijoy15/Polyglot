package com.problems.learning.algo.string;

public class StringReverseSpecialChar {

    //Sliding Window
    public String reverseWithSpecialCharIntact(String s) {
        char[] charArray = s.toCharArray();
        int left = 0, right = charArray.length - 1;
        while (left < right) {
            char firstChar = charArray[left];
            char secondChar = charArray[right];
            if(isAlphaNumeric(firstChar) && isAlphaNumeric(secondChar)) {
                charArray[left++] = secondChar;
                charArray[right--] = firstChar;
                continue;
            }
            if(!isAlphaNumeric(firstChar)) {
                left++;
            }
            if(!isAlphaNumeric(secondChar)) {
                right--;
            }
        }
        return new String(charArray);
    }

    private static boolean isAlphaNumeric(char temp) {
        return (temp >= 65 && temp <= 90)
                || (temp >= 97 && temp <= 123)
                || (temp >= 48 && temp <= 57);
    }
}
