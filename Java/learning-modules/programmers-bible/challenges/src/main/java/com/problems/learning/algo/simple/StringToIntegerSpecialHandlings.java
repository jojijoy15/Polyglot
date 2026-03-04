package com.problems.learning.algo.simple;

/*
 You are given a string str that may contain a mixture of characters including digits, spaces,
    alphabetic characters, and signs ('+' or '-').
 Your task is to convert the string into an integer following these specific rules:
    - Leading Alphabets: If the string starts with alphabetic characters, the function should return -100.
    - Leading Spaces: Any leading spaces in the string should be ignored.
    - Sign Handling: The string may contain a single '+' or '-' sign that indicates the sign of the resulting number.
        This sign should be considered only if it appears before any digits.
    - Digit Conversion: Convert consecutive digits into the corresponding integer value.
    - Out of Range Handling: If the resulting integer is outside the range of a 32-bit signed integer:
        Return Integer.MAX_VALUE (2,147,483,647) if the number is too large.
        Return Integer.MIN_VALUE (-2,147,483,648) if the number is too small.
    - Alphabets After Digits: If any alphabetic characters appear after the digits, stop processing further characters
        and return the integer formed up to that point.
 */
public class StringToIntegerSpecialHandlings {

    public Integer asciiToInt(String number) {
        String trimmed = number.trim();
        char firstCharacter = trimmed.charAt(0);
        if(!isSign(firstCharacter) && !isNumber(firstCharacter)) {
            return -100;
        }
        char[] characters = trimmed.toCharArray();

        int result = 0;
        boolean isNegative = firstCharacter == '-';
        int i = isNegative || isSign(firstCharacter) ? 1 : 0;
        for(; i < characters.length; i++ ) {
            if(!isNumber(characters[i])) {
                break;
            }
            result = formNumber(result, characters[i],  isNegative);
            if( result == Integer.MIN_VALUE || result == Integer.MAX_VALUE) {
                return result;
            }
        }
        return isSign(firstCharacter) && isNegative ? result * -1 : result;

    }

    private boolean isNumber(Character ch) {
        int val = convertToNumber(ch);
        return val >= 0 && val <= 9;
    }

    private int convertToNumber(Character ch) {
        return ch - '0';
    }

    private boolean isSign(Character ch) {
        return (ch == '+' || ch == '-');
    }


    private int formNumber(int number, int digit, boolean isNegative) {
        long value = (long) number <<1;
        int count = 3;
        while(!(value <= Integer.MAX_VALUE) && count-- > 0 ){
            value <<= 1;
        }
        if(value >= Integer.MAX_VALUE) {
            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        value += value << 1;
        if(value >= Integer.MAX_VALUE) {
            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        return number * 10 + (digit - '0');
    }
}
