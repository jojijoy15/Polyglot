package com.problems.learning.fooddelivery.enums;

import lombok.Getter;

@Getter
public enum Urgency {

    SCHEDULED(1), NORMAL(2), URGENT(3);

    private final int priority;

    Urgency(int priority) {
        this.priority = priority;
    }

}
