package com.problems.learning.designpatterns.structural.flyweight.factory;

import com.problems.learning.designpatterns.structural.flyweight.invariant.FeatureFlag;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FeatureFlagFactory {

    private static final ConcurrentMap<String, FeatureFlag> cache = new ConcurrentHashMap<>();

    public static FeatureFlag getFeature(String name, int rollout) {
        return cache.computeIfAbsent(name, k -> {
            System.out.println("Creating FeatureFlag: " + k);
            return new FeatureFlag(k, rollout);
        });
    }
}