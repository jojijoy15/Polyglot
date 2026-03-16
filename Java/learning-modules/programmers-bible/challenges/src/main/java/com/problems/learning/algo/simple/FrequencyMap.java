package com.problems.learning.algo.simple;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FrequencyMap {

    public List<Character> lastOneSurvives(Map<Character,Integer> frequencyMap){
        int rounds = frequencyMap.values().stream().mapToInt(Integer::intValue).max().getAsInt();
        List<Character> lastChar = Collections.emptyList();
        while(rounds-- != 1){
            for(Map.Entry<Character,Integer> entry : frequencyMap.entrySet()){
                if(entry.getValue() != 0) {
                    entry.setValue(entry.getValue()-1);
                }
            }
            frequencyMap.entrySet()
                .removeIf(entry-> entry.getValue() == 0);
        }
        lastChar = frequencyMap
                .keySet().stream()
                .toList();
        return lastChar;
    }
}
