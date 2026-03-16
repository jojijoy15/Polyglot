package com.problems.learning.solid.ocp.complier;

import com.problems.learning.solid.ocp.complier.vehicles.Vehicle;

public class ParkingFeeEstimate {

    /*
          If new vehicle needs to be added then no change in the code?
     */
    public double calculateFee(Vehicle vehicle, Slot slot) {
       return vehicle.calculateFee(slot);
    }
}
