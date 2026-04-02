package com.problems.learning.algo.string;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class NonRepeatingCharacter {

    public Character findNonRepeatingCharacter(String sequence) {
        /*
        HashSet<Character> set = new HashSet<>();
        char[] chars = sequence.toCharArray();
        for (int i = 0; i < sequence.length(); i++) {
            if(!set.add(chars[i])) {
                set.remove(chars[i]);
            }
        }
        return set.stream().findFirst().get(); //One Character
        */
        /*
        for (int i = 0; i < sequence.length(); i++) {
            char character = sequence.charAt(i);
            int firstIndex = sequence.indexOf(character);
            int lastIndex = sequence.lastIndexOf(character);
            if(firstIndex == lastIndex){
                return character;
            }
        }*/
        if (sequence == null || sequence.isEmpty()) {
            return null;
        }

        Map<Character, Integer> frequencyMap = new LinkedHashMap<>();
        for (char c : sequence.toCharArray()) {
            frequencyMap.merge(c, 1, Integer::sum);
        }

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return null;

    }
}
