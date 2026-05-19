package com.problems.learning.system.parkinglot;

/*
    Represents an Entry or Exit gate in the parking lot.

    Each gate delegates to ParkingLot for actual park/unpark operations.
    Multiple gates can operate concurrently — thread safety is handled by ParkingLot.

    Usage:
        Gate entryGate1 = parkingLot.addGate(1, GateType.ENTRY);
        Gate exitGate1  = parkingLot.addGate(2, GateType.EXIT);
        Gate dualGate   = parkingLot.addGate(3, GateType.BOTH);

        entryGate1.enter(vehicle);       // parks the vehicle
        exitGate1.exit("KA-01-1234", wallet);  // unparks and charges
*/
public class Gate {

    public enum GateType {
        ENTRY,  // can only accept vehicles entering
        EXIT,   // can only accept vehicles exiting
        BOTH    // can handle entry and exit
    }

    private final int gateNumber;
    private final GateType gateType;
    private final ParkingLot parkingLot;

    public Gate(int gateNumber, GateType gateType, ParkingLot parkingLot) {
        this.gateNumber = gateNumber;
        this.gateType = gateType;
        this.parkingLot = parkingLot;
    }

    /*
        Vehicle enters through this gate.
        Only allowed if gate type is ENTRY or BOTH.
    */
    public Ticket enter(Vehicle vehicle) {
        if (gateType == GateType.EXIT) {
            System.out.println("🚫 Gate-" + gateNumber + " is EXIT only. Cannot enter here.");
            return null;
        }
        System.out.println("🚪 Gate-" + gateNumber + " [ENTRY]: " + vehicle);
        return parkingLot.parkVehicle(vehicle);
    }

    /*
        Vehicle exits through this gate and pays.
        Only allowed if gate type is EXIT or BOTH.
    */
    public Ticket exit(String vehicleNumber, Wallet wallet) {
        if (gateType == GateType.ENTRY) {
            System.out.println("🚫 Gate-" + gateNumber + " is ENTRY only. Cannot exit here.");
            return null;
        }
        System.out.println("🚪 Gate-" + gateNumber + " [EXIT]: Vehicle " + vehicleNumber);
        return parkingLot.unparkVehicle(vehicleNumber, wallet);
    }

    public int getGateNumber() {
        return gateNumber;
    }

    public GateType getGateType() {
        return gateType;
    }

    @Override
    public String toString() {
        return "Gate-" + gateNumber + " (" + gateType + ")";
    }
}

