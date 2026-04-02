package com.problems.learning.designpatterns.structural.adapter;

import com.problems.learning.designpatterns.structural.adapter.adapters.ThreePinToTwoPinAdapter;
import com.problems.learning.designpatterns.structural.adapter.charger.GroundedCharger;
import com.problems.learning.designpatterns.structural.adapter.charger.ThreePinCharger;
import com.problems.learning.designpatterns.structural.adapter.charger.TwoPinCharger;
import com.problems.learning.designpatterns.structural.adapter.devices.SimpleMobile;
import com.problems.learning.designpatterns.structural.adapter.outlet.Outlet;
import com.problems.learning.designpatterns.structural.adapter.outlet.OutletWith2Pin;


/*
   Note:
    * The Adapter Design Pattern in Java converts the interface of a class into another interface that clients expect,
    enabling compatibility.
 */
public class ChargingRoom {

    public static void main(String[] args) {
        GroundedCharger threePinCharger = new ThreePinCharger();
        SimpleMobile simpleMobile = new SimpleMobile(threePinCharger);

        TwoPinCharger twoPinCharger = new ThreePinToTwoPinAdapter(threePinCharger);
        Outlet outlet = new OutletWith2Pin(twoPinCharger);
        outlet.connect();
    }
}
