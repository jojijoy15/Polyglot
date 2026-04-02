package com.problems.learning.designpatterns.structural.adapter.charger;

import lombok.ToString;

@ToString
public class ThreePinCharger implements GroundedCharger {

    public void groundedCharging(int numberOfPins) {
        System.out.println("Charger connected to 3 Pin outlet and charging in progress...");
    }
}
