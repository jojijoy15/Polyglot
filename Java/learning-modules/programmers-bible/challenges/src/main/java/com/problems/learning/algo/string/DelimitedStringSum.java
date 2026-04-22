 package com.problems.learning.algo.string;

 import java.util.Arrays;

 public class DelimitedStringSum {

    public int sum(String input) {

        if(null == input || input.isEmpty()) {
            return 0;
        }
        char[] chars = input.toCharArray();
        int sum = 0;
        int left = 0;
        for(int right = 0; right < chars.length; right++) {

            if((chars[right] != '_') && (chars[right] < 48 || chars[right] > 57)) {
                left++;
            }
            else if (chars[right]  >= 48 && chars[right] <= 57) {
                left = right;
                while(right < chars.length && chars[right] != '_') {
                   right++;
                }
                char[] digits = Arrays.copyOfRange(chars, left, right);
                int number = toInt(digits);
                sum += number;
            }
        }
        return sum;
    }

    private int toInt(char[] chars){
        try {
            return Integer.parseInt(String.valueOf(chars));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
