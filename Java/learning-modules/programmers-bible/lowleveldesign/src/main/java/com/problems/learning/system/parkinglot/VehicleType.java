package com.problems.learning.system.parkinglot;

/*
    Vehicle types based on size/wheels.
    Each type has its own hourly parking rate.
    Size determines slot flexibility — smaller vehicles can fit in larger slots.

    Slot Flexibility:
        MOTORCYCLE (size 1) → can fit in MOTORCYCLE, CAR, or BUS slot
        CAR        (size 2) → can fit in CAR or BUS slot
        BUS        (size 3) → can only fit in BUS slot
*/
public enum VehicleType {

    MOTORCYCLE(2, 10.0, 1),
    CAR(4, 20.0, 2),
    BUS(6, 50.0, 3);

    private final int wheels;
    private final double hourlyRate;
    private final int size; // 1 = smallest, 3 = largest

    VehicleType(int wheels, double hourlyRate, int size) {
        this.wheels = wheels;
        this.hourlyRate = hourlyRate;
        this.size = size;
    }

    public int getWheels() {
        return wheels;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public int getSize() {
        return size;
    }

    /*
        Can this vehicle fit in a slot designed for 'slotType'?
        A vehicle can fit if its size ≤ slot's supported size.
    */
    public boolean canFitIn(VehicleType slotType) {
        return this.size <= slotType.size;
    }
}
