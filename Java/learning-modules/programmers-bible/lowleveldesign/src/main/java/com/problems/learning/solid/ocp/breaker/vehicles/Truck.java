package com.problems.learning.solid.ocp.breaker.vehicles;

public class Truck extends Vehicle {


    public Truck(VehicleType type, int wheels, String model) {
        super(type, wheels, model);
    }

    @Override
    public void drive() {
        System.out.println("Driving " + this.model);
    }
}
