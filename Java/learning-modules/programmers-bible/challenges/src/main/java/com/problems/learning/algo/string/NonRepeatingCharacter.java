package com.problems.learning.algo.string;

import java.util.HashSet;

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
        for (int i = 0; i < sequence.length(); i++) {
            char character = sequence.charAt(i);
            int firstIndex = sequence.indexOf(character);
            int lastIndex = sequence.lastIndexOf(character);
            if(firstIndex == lastIndex){
                return character;
            }
        }
        return null;

    }
}
