package com.problems.learning.designpatterns.behavioral.mediator.aircraft;

import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftStatus;
import com.problems.learning.designpatterns.behavioral.mediator.aircraft.enums.AircraftType;
import com.problems.learning.designpatterns.behavioral.mediator.tower.AirTrafficControlMediator;

/**
 * Commercial flights have highest landing priority — most passengers.
 * They use long runways and need gate assignments.
 */
public class CommercialFlight extends Aircraft {

    private final int passengerCount;

    public CommercialFlight(String callSign, int altitude, int fuel,
                            int passengerCount,
                            AirTrafficControlMediator mediator) {
        super(callSign, AircraftType.COMMERCIAL, altitude, fuel, mediator);
        this.passengerCount = passengerCount;
    }

    @Override
    public void performLanding() {
        System.out.printf("   🛬 [%s] COMMERCIAL: Initiating approach on runway %d "
                        + "(%d passengers aboard)%n",
                callSign, assignedRunway, passengerCount);
        status = AircraftStatus.LANDING;
        altitude = 0;
        status = AircraftStatus.LANDED;
        System.out.printf("   ✅ [%s] Landed safely → taxiing to gate %d%n",
                callSign, assignedGate);
        releaseRunway();
        status = AircraftStatus.AT_GATE;
    }

    @Override
    public void performTakeoff() {
        System.out.printf("   🛫 [%s] COMMERCIAL: Full thrust — departing on runway %d%n",
                callSign, assignedRunway);
        status = AircraftStatus.TAKING_OFF;
        altitude = 35000;
        status = AircraftStatus.AIRBORNE;
        releaseRunway();
        System.out.printf("   ✅ [%s] Airborne at 35,000ft%n", callSign);
    }

    @Override
    public void respondToEmergency(String emergencyCallSign) {
        // Commercial flights increase separation distance
        System.out.printf("   ✈️  [%s] Increasing separation — holding at current position%n",
                callSign);
        altitude += 1000;
    }

    @Override
    public int getLandingPriority() {
        // Higher fuel burn + more passengers = higher priority
        int fuelPriority = fuel < 20 ? 100 : fuel < 40 ? 50 : 0;
        int typePriority = 40;
        return typePriority + fuelPriority;
    }
}