package com.problems.learning.designpatterns.behavioral.mediator.aircraft;

import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftStatus;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftType;
import com.problems.learning.designpatterns.behavioral.mediator.tower.AirTrafficControlMediator;

/**
 * Helicopters use helipads — don't compete with runways.
 * They get emergency priority automatically.
 */
public class Helicopter extends Aircraft {

    private final boolean isMedical;

    public Helicopter(String callSign, int altitude, int fuel,
                      boolean isMedical,
                      AirTrafficControlMediator mediator) {
        super(callSign, AircraftType.HELICOPTER, altitude, fuel, mediator);
        this.isMedical = isMedical;
    }

    @Override
    public void performLanding() {
        System.out.printf("   🚁 [%s] HELICOPTER: %sVertical descent to helipad%n",
                callSign, isMedical ? "MEDICAL — " : "");
        status = AircraftStatus.LANDING;
        altitude = 0;
        status = AircraftStatus.LANDED;
        System.out.printf("   ✅ [%s] Helicopter landed on helipad %d%n",
                callSign, assignedGate);
        releaseRunway();
        status = AircraftStatus.AT_GATE;
    }

    @Override
    public void performTakeoff() {
        System.out.printf("   🚁 [%s] HELICOPTER: Vertical takeoff%n", callSign);
        status = AircraftStatus.TAKING_OFF;
        altitude = 3000;
        status = AircraftStatus.AIRBORNE;
        releaseRunway();
        System.out.printf("   ✅ [%s] Helicopter airborne at 3,000ft%n", callSign);
    }

    @Override
    public void respondToEmergency(String emergencyCallSign) {
        if (isMedical) {
            System.out.printf("   🏥 [%s] MEDICAL HELICOPTER: Proceeding to emergency "
                    + "support position for %s%n", callSign, emergencyCallSign);
        } else {
            System.out.printf("   🚁 [%s] Hovering clear of emergency zone%n", callSign);
        }
    }

    @Override
    public int getLandingPriority() {
        return isMedical ? 200 : 30;   // medical helicopters always first
    }
}