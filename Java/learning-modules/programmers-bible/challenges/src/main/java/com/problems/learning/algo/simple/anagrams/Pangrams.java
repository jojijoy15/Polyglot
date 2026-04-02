package com.problems.learning.algo.simple.anagrams;

public class Pangrams {

    String findMissingAlphabet(String input) {
        char[] characters = input.toCharArray();
        int[]  frequencies = new int[26];
        for (char c : characters) {
            if(c == ' ')
                continue;
            if(c >= 65 && c <= 91) {
                frequencies[c - 'A'] ++;
            } else {
                frequencies[c - 'a']++;
            }
        }
        StringBuilder missingCharacter = new StringBuilder();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] == 0) {
                missingCharacter.append((char)(i + 'a'));
            }
        }
        return missingCharacter.toString();
    }
}
