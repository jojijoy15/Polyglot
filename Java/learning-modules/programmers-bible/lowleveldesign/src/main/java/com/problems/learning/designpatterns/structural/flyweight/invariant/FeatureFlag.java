package com.problems.learning.designpatterns.structural.flyweight.invariant;

import lombok.Getter;

//Note: Immutable
public final class FeatureFlag {

    @Getter
    private final String name;
    private final int rolloutPercentage;

    public FeatureFlag(String name, int rolloutPercentage) {
        this.name = name;
        this.rolloutPercentage = rolloutPercentage;
    }

    // Uses extrinsic state (userId) to decide
    public boolean isEnabled(String userId) {
        int hash = Math.abs(userId.hashCode() % 100);
        return hash < rolloutPercentage;
    }

}