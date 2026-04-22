package com.problems.learning.designpatterns.structural.adapter.outlet;

import com.problems.learning.designpatterns.structural.adapter.charger.TwoPinCharger;

public class OutletWith2Pin implements Outlet {

    private int numberOfPins;
    private TwoPinCharger charger;

    public OutletWith2Pin(TwoPinCharger charger) {
        this.numberOfPins = 2;
        this.charger = charger;
    }

    @Override
    public void connect() {
        System.out.printf("Connected to the %d pin charger, charging began with %s\r\n", numberOfPins, charger);
        this.charger.charging(numberOfPins);
    }
}
