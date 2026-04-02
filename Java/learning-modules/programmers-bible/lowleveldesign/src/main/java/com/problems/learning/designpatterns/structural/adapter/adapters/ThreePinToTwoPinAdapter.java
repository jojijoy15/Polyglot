package com.problems.learning.designpatterns.structural.adapter.adapters;

import com.problems.learning.designpatterns.structural.adapter.charger.GroundedCharger;
import com.problems.learning.designpatterns.structural.adapter.charger.TwoPinCharger;

public class ThreePinToTwoPinAdapter implements TwoPinCharger {

    private GroundedCharger threePinCharger;

    public ThreePinToTwoPinAdapter(GroundedCharger threePinCharger) {
        this.threePinCharger = threePinCharger;
    }

    @Override
    public boolean charging(int numberOfPins) {
        if (numberOfPins == 2) {
            this.threePinCharger.groundedCharging(numberOfPins + 1);
            System.out.printf("Grounded charging on 3 Pin outlet.. %s\r\n", threePinCharger);
            return true;
        } else {
            return false;
        }
    }
}
