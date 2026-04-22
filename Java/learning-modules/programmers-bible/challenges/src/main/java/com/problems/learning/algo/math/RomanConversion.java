package com.problems.learning.algo.math;

import java.util.List;
import java.util.Map;

public class RomanConversion {

    private static final List<Map.Entry<Integer, String>> conversionMap = List.of(
            Map.entry(1,"I"),
            Map.entry(4, "IV"),
            Map.entry(5,"V"),
            Map.entry(9, "IX"),
            Map.entry(10,"X"),
            Map.entry(40,"XL"),
            Map.entry(50, "L"),
            Map.entry(90, "XC"),
            Map.entry(100,"C"),
            Map.entry(400, "CD"),
            Map.entry(500, "D"),
            Map.entry(900, "CM"),
            Map.entry(1000, "M")
    );


    public static final Map<Character, Integer> romanToInt = Map.of(
            'I', 1, 'V', 5, 'X', 10, 'L', 50,
            'C', 100, 'D', 500, 'M', 1000
    );

    public String toRoman(int input) {
        StringBuilder result = new StringBuilder();
        for(int index = conversionMap.size() - 1; index > -1; index--){
            Map.Entry<Integer, String> entry = conversionMap.get(index);
            Integer key = entry.getKey();
            int count = input/key;
            if(count>0) { //should replace repeat by while when repeat is not available
                result.append(entry.getValue().repeat(count));
                input = input % key;
            }
        }
        return result.toString();
    }

    public int toInt(String input) {
        int result = 0;
        for (int index = 0; index < input.length(); index++) {
            int current = romanToInt.get(input.charAt(index));
            int next = (index + 1 < input.length()) ? romanToInt.get(input.charAt(index + 1)) : 0;

            if (current < next) {
                result -= current; // e.g., I before V → subtract 1
            } else {
                result += current;
            }
        }
        return result;

    }

}
