package com.problems.learning.system.parkinglot;

/*
    Represents a single parking slot on a floor.
    Implements Comparable so it can be stored in a TreeSet
    for O(log n) assignment of the lowest-numbered available slot.
*/
public class ParkingSlot implements Comparable<ParkingSlot> {

    private final int slotNumber;
    private final int floorNumber;
    private final VehicleType supportedType;
    private boolean occupied;

    public ParkingSlot(int slotNumber, int floorNumber, VehicleType supportedType) {
        this.slotNumber = slotNumber;
        this.floorNumber = floorNumber;
        this.supportedType = supportedType;
        this.occupied = false;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public VehicleType getSupportedType() {
        return supportedType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        this.occupied = true;
    }

    public void free() {
        this.occupied = false;
    }

    // TreeSet ordering: by slot number (lowest first → assigned first)
    @Override
    public int compareTo(ParkingSlot other) {
        return Integer.compare(this.slotNumber, other.slotNumber);
    }

    @Override
    public String toString() {
        return "Floor-" + floorNumber + " Slot-" + slotNumber + " (" + supportedType + ")";
    }
}

