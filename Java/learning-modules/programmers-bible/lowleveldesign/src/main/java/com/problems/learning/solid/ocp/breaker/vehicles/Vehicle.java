package com.problems.learning.solid.ocp.breaker.vehicles;

import lombok.Getter;

@Getter
public abstract class Vehicle {

    protected VehicleType type;
    protected int wheels;
    protected String model;

    public Vehicle(VehicleType type, int wheels, String model) {
        this.type = type;
        this.wheels = wheels;
        this.model = model;
    }

    public abstract void drive();
}
