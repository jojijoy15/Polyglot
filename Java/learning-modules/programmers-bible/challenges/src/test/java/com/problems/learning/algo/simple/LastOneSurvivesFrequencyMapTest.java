package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LastOneSurvivesFrequencyMapTest {

    LastOneSurvivesFrequencyMap lastOneSurvivesFrequencyMap = new LastOneSurvivesFrequencyMap();

    @Test
    void lastOneSurvives() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 1);
        map.put('b', 2);
        map.put('c', 5);
        List<Character> characters = lastOneSurvivesFrequencyMap.lastOneSurvives(map);
        assertThat(characters).isEqualTo(List.of('c'));
    }
}