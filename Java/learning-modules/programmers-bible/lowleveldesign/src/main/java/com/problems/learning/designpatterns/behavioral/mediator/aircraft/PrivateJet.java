package com.problems.learning.designpatterns.behavioral.mediator.aircraft;

import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftStatus;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftType;
import com.problems.learning.designpatterns.behavioral.mediator.tower.AirTrafficControlMediator;

/**
 * Private jets have lowest priority — flexible, can divert easily.
 * They use shorter runways and prefer quick turnarounds.
 */
public class PrivateJet extends Aircraft {

    private final String ownerName;

    public PrivateJet(String callSign, int altitude, int fuel,
                      String ownerName,
                      AirTrafficControlMediator mediator) {
        super(callSign, AircraftType.PRIVATE_JET, altitude, fuel, mediator);
        this.ownerName = ownerName;
    }

    @Override
    public void performLanding() {
        System.out.printf("   🛬 [%s] PRIVATE JET: Smooth approach on runway %d "
                + "(owner: %s)%n", callSign, assignedRunway, ownerName);
        status = AircraftStatus.LANDING;
        altitude = 0;
        status = AircraftStatus.LANDED;
        System.out.printf("   ✅ [%s] Private jet parked at gate %d%n",
                callSign, assignedGate);
        releaseRunway();
        status = AircraftStatus.AT_GATE;
    }

    @Override
    public void performTakeoff() {
        System.out.printf("   🛫 [%s] PRIVATE JET: Quick departure on runway %d%n",
                callSign, assignedRunway);
        status = AircraftStatus.TAKING_OFF;
        altitude = 42000;   // jets fly higher
        status = AircraftStatus.AIRBORNE;
        releaseRunway();
        System.out.printf("   ✅ [%s] Private jet cruising at 42,000ft%n", callSign);
    }

    @Override
    public void respondToEmergency(String emergencyCallSign) {
        System.out.printf("   ✈️  [%s] Private jet diverting — "
                + "giving way to emergency %s%n", callSign, emergencyCallSign);
    }

    @Override
    public int getLandingPriority() {
        int fuelPriority = fuel < 15 ? 60 : 0;
        return 10 + fuelPriority;   // lowest base priority
    }
}