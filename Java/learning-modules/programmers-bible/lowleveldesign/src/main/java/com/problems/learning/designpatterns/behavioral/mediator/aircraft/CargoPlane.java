package com.problems.learning.designpatterns.behavioral.mediator.aircraft;

import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftStatus;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftType;
import com.problems.learning.designpatterns.behavioral.mediator.tower.AirTrafficControlMediator;

/**
 * Cargo planes have medium priority — no passengers at risk.
 * They prefer specific cargo runways and can hold longer.
 */
public class CargoPlane extends Aircraft {

    private final double cargoWeightTons;

    public CargoPlane(String callSign, int altitude, int fuel,
                      double cargoWeightTons,
                      AirTrafficControlMediator mediator) {
        super(callSign, AircraftType.CARGO, altitude, fuel, mediator);
        this.cargoWeightTons = cargoWeightTons;
    }

    @Override
    public void performLanding() {
        System.out.printf("   🛬 [%s] CARGO: Heavy landing approach on runway %d "
                        + "(%.1f tons of cargo)%n",
                callSign, assignedRunway, cargoWeightTons);
        status = AircraftStatus.LANDING;
        altitude = 0;
        status = AircraftStatus.LANDED;
        System.out.printf("   ✅ [%s] Landed — proceeding to cargo terminal%n", callSign);
        releaseRunway();
        status = AircraftStatus.AT_GATE;
    }

    @Override
    public void performTakeoff() {
        System.out.printf("   🛫 [%s] CARGO: Heavy takeoff on runway %d "
                        + "(%.1f tons)%n",
                callSign, assignedRunway, cargoWeightTons);
        status = AircraftStatus.TAKING_OFF;
        altitude = 28000;
        status = AircraftStatus.AIRBORNE;
        releaseRunway();
        System.out.printf("   ✅ [%s] Cargo airborne at 28,000ft%n", callSign);
    }

    @Override
    public void respondToEmergency(String emergencyCallSign) {
        System.out.printf("   🚛 [%s] Cargo plane diverting to secondary runway — "
                + "clearing path for %s%n", callSign, emergencyCallSign);
    }

    @Override
    public int getLandingPriority() {
        int fuelPriority = fuel < 20 ? 80 : 0;
        return 25 + fuelPriority;
    }
}