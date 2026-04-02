package com.problems.learning.algo.simple;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LRUIP {

    private final static Pattern ipPattern  = Pattern.compile("^(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");

    public String findLRUIP(List<String> logs) {
        List<String> ipAddresses = logs.stream()
                .map(ipPattern::matcher)
                .filter(Matcher::find)
                .map(m -> m.group(1))
                .toList();

        LinkedHashMap<String, Integer> lru = new LinkedHashMap<>(16, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 100;
          }

        };
        for (int i = 0; i< ipAddresses.size(); i++) {
            lru.merge(ipAddresses.get(i), i, (k, v) -> v);
        }
        System.out.println("LRU Cache: " + lru);
        return lru.keySet().iterator().next();
    }

}
