package com.problems.learning.designpatterns.structural.flyweight.client;

import com.problems.learning.designpatterns.structural.flyweight.factory.FeatureFlagFactory;
import com.problems.learning.designpatterns.structural.flyweight.invariant.FeatureFlag;

import java.util.List;
import java.util.stream.IntStream;

/*
   Note:
    * The primary goal of the Flyweight pattern is to share as much data as possible among similar objects,
        thereby improving efficiency and performance.
    *
 */
public class FeatureFlagCheckerClient {

    public static void main(String[] args) {

        List<String> featureNames = List.of("NEW_CHECKOUT", "CART", "UPDATE_PROFILE");
        List<FeatureFlag> flags = featureNames.stream()
                .map(featureName -> FeatureFlagFactory.getFeature(featureName, 30))
                .toList();

        // Different users → same feature object
        IntStream.range(1, 100).forEach(i -> {
            System.out.println(flags.get(0).isEnabled("user" + i));
        });

        FeatureFlag feature2 = FeatureFlagFactory.getFeature("NEW_CHECKOUT", 30);

        System.out.println("Same Feature flag : " + (flags.get(0) == feature2));
    }
}