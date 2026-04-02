package com.problems.learning.designpatterns.structural.adapter.charger;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SamsungTwoPinCharger implements TwoPinCharger {

    private int numberOfPins;

    public SamsungTwoPinCharger() {
        this.numberOfPins = 2;
    }

    @Override
    public boolean charging(int numberOfPins) {
        System.out.printf("Socket connected to Pin outlet with %d pins and charging in progress...\r\n", numberOfPins);
        return this.numberOfPins == numberOfPins;
    }
}
