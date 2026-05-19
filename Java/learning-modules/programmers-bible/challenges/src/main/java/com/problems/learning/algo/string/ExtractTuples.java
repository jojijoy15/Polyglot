package com.problems.learning.algo.string;

import java.util.*;

public class ExtractTuples {

    public List<String> getTuples(String word, int size) {
        Set<String> tuples = new LinkedHashSet<>();
        for(int i = 0; i < word.length() - size + 1; i++){
            tuples.add(word.substring(i, i + size));
        }
        return new ArrayList<>(tuples);
    }
}
