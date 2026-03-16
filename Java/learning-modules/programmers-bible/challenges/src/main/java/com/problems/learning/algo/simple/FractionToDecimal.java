package com.problems.learning.algo.simple;

import java.util.HashMap;
import java.util.Map;

public class FractionToDecimal {

    public static String fractionToDecimal(int numerator, int denominator) {

        if (numerator == 0) return "0";
        StringBuilder result = new StringBuilder();

        // handle negative sign
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }

        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);

        // integer part
        result.append(num / den);

        long remainder = num % den;
        if (remainder == 0) return result.toString();

        result.append(".");
        Map<Long, Integer> map = new HashMap<>();

        while (remainder != 0) { //cycle detection logic
            if (map.containsKey(remainder)) {
                int index = map.get(remainder);
                result.insert(index, "(");
                result.append(")");
                break;
            }
            map.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }

        return result.toString();
    }

    public static void main(String[] args) {

        System.out.println(fractionToDecimal(1,2)); // 0.5
        System.out.println(fractionToDecimal(1,3)); // 0.(3)
        System.out.println(fractionToDecimal(4,7)); // 0.(571428)
    }
}