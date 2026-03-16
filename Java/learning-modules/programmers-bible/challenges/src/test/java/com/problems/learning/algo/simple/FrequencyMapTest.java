package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FrequencyMapTest {

    FrequencyMap frequencyMap = new FrequencyMap();

    @Test
    void lastOneSurvives() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 1);
        map.put('b', 2);
        map.put('c', 5);
        List<Character> characters = frequencyMap.lastOneSurvives(map);
        assertThat(characters).isEqualTo(List.of('c'));
    }
}