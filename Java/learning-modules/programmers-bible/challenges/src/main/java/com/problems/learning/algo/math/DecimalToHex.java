package com.problems.learning.algo.math;

import com.problems.learning.tags.Easy;

@Easy
public class DecimalToHex {

    public static String convertToHex(int decimal) {
        // Handle the edge case of 0 explicitly
        if (decimal == 0) {
            return "0";
        }

        StringBuilder hexResult = new StringBuilder();
        
        // Lookup array for quick conversion of remainders to hex characters
        char[] hexChars = "0123456789ABCDEF".toCharArray();

        while (decimal > 0) {
            int remainder = decimal % 16;
            hexResult.append(hexChars[remainder]);
            decimal = decimal / 16;
        }

        // The remainders are collected in reverse order, so we must reverse the string
        return hexResult.reverse().toString();
    }

}