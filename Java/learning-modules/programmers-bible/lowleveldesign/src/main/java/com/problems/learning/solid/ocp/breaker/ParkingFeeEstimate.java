package com.problems.learning.solid.ocp.breaker;

import com.problems.learning.solid.ocp.breaker.vehicles.Vehicle;

import java.time.Duration;

public class ParkingFeeEstimate {

    /*
        What is the problem
            If new vehicle needs to be added then change the code?
     */
    public double calculateFee(Vehicle vehicle, Slot slot) {
        switch (vehicle.getType()) {
            case CAR -> {
                long minutes = Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes();
                return minutes * 1.5;
            }
            case TRUCK -> {
                long minutes = Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes();
                return minutes * 3.4;
            }
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
