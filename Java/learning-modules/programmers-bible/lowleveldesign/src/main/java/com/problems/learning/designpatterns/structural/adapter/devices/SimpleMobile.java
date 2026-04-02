package com.problems.learning.designpatterns.structural.adapter.devices;

import com.problems.learning.designpatterns.structural.adapter.charger.GroundedCharger;

public class SimpleMobile {

    private GroundedCharger charger;

    public SimpleMobile(GroundedCharger charger) {
        this.charger = charger;
    }

    public void recharge() {
        this.charger.groundedCharging(3);
    }

}
