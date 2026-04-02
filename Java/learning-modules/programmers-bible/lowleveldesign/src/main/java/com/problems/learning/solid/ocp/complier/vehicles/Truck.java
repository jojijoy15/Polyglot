package com.problems.learning.solid.ocp.complier.vehicles;

import com.problems.learning.solid.ocp.complier.Slot;

import java.time.Duration;

public class Truck extends Vehicle {

    public Truck(VehicleType type, int wheels, String model) {
        super(type, wheels, model);
    }

    @Override
    public void drive() {
        System.out.println("Driving " + this.model);
    }

    @Override
    public double calculateFee(Slot slot) {
        long minutes = Duration.between(slot.getStartTime(), slot.getEndTime()).toHours();
        return minutes * 3.4;
    }
}
