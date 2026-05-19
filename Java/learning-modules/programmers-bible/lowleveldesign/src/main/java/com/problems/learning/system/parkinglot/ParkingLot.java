package com.problems.learning.system.parkinglot;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
    Main orchestrator for the Parking Lot system.

    Data Structure Choices:
        ┌────────────────────────────────────────────────────────────────────┐
        │  ConcurrentHashMap<String, Ticket> →  O(1) thread-safe lookup    │
        │  List<ParkingFloor>                →  iterate floors for slot     │
        │  TreeSet<ParkingSlot>              →  O(log n) assign/free       │
        │  HashSet<String>                   →  O(1) blacklist check       │
        └────────────────────────────────────────────────────────────────────┘

    Thread Safety:
        - ConcurrentHashMap for activeTickets (lock-free reads, segment-level writes)
        - ParkingFloor uses ReentrantReadWriteLock internally
        - BlacklistService uses ConcurrentHashMap.newKeySet()
        - synchronized parkVehicle/unparkVehicle to prevent race between check & assign

    Responsibilities:
        1. Park a vehicle → find first available slot across floors (with flexibility)
        2. Unpark a vehicle → lookup by vehicle number, calculate fare, process payment
        3. Search a vehicle → O(1) by vehicle number
        4. Deny entry to blacklisted vehicles
        5. Entry/Exit via Gate objects for multi-gate concurrency
*/
public class ParkingLot {

    private final String name;
    private final List<ParkingFloor> floors;
    // vehicleNumber → Ticket : O(1) thread-safe lookup
    private final Map<String, Ticket> activeTickets;
    private final BlacklistService blacklistService;
    private final PaymentService paymentService;
    private final List<Gate> gates;

    public ParkingLot(String name, List<ParkingFloor> floors) {
        this.name = name;
        this.floors = floors;
        this.activeTickets = new ConcurrentHashMap<>();
        this.blacklistService = new BlacklistService();
        this.paymentService = new PaymentService(blacklistService);
        this.gates = new ArrayList<>();
    }

    /*
        Register entry/exit gates. Each gate holds a reference back to this ParkingLot.
    */
    public Gate addGate(int gateNumber, Gate.GateType gateType) {
        Gate gate = new Gate(gateNumber, gateType, this);
        gates.add(gate);
        System.out.println("🚧 Gate-" + gateNumber + " (" + gateType + ") registered.");
        return gate;
    }

    public List<Gate> getGates() {
        return Collections.unmodifiableList(gates);
    }

    /*
        Park a vehicle.
        - Reject if blacklisted
        - Reject if already parked
        - Iterate floors to find first available slot for vehicle type
          (with slot flexibility — smaller vehicle can use larger slot)
        - Issue a ticket

        synchronized to prevent race condition between containsKey check and put.
        Time: O(F) where F = number of floors (slot assignment is O(log n) per floor)
    */
    public synchronized Ticket parkVehicle(Vehicle vehicle) {
        String number = vehicle.getVehicleNumber();

        if (blacklistService.isBlacklisted(number)) {
            System.out.println("⛔ DENIED: Vehicle " + number + " is blacklisted. Clear dues first.");
            return null;
        }

        if (activeTickets.containsKey(number)) {
            System.out.println("⚠️ Vehicle " + number + " is already parked.");
            return null;
        }

        // Pass 1: Try exact type match across all floors first
        for (ParkingFloor floor : floors) {
            ParkingSlot slot = floor.assignExactSlot(vehicle.getType());
            if (slot != null) {
                Ticket ticket = new Ticket(vehicle, slot);
                activeTickets.put(number, ticket);
                System.out.println("🅿️ Parked " + vehicle + " at " + slot + " | Ticket: " + ticket.getTicketId());
                return ticket;
            }
        }

        // Pass 2: Flexibility — try larger slot types across all floors
        for (ParkingFloor floor : floors) {
            ParkingSlot slot = floor.assignFlexibleSlot(vehicle.getType());
            if (slot != null) {
                Ticket ticket = new Ticket(vehicle, slot);
                activeTickets.put(number, ticket);
                System.out.println("🅿️ Parked " + vehicle + " at " + slot + " | Ticket: " + ticket.getTicketId());
                return ticket;
            }
        }

        System.out.println("🚫 No available slot for " + vehicle.getType() + " on any floor.");
        return null;
    }

    // Park with custom entry time (for testing fare calculation)
    public synchronized Ticket parkVehicle(Vehicle vehicle, LocalDateTime entryTime) {
        String number = vehicle.getVehicleNumber();

        if (blacklistService.isBlacklisted(number)) {
            System.out.println("⛔ DENIED: Vehicle " + number + " is blacklisted.");
            return null;
        }
        if (activeTickets.containsKey(number)) {
            System.out.println("⚠️ Vehicle " + number + " is already parked.");
            return null;
        }

        // Pass 1: exact match
        for (ParkingFloor floor : floors) {
            ParkingSlot slot = floor.assignExactSlot(vehicle.getType());
            if (slot != null) {
                Ticket ticket = new Ticket(vehicle, slot, entryTime);
                activeTickets.put(number, ticket);
                System.out.println("🅿️ Parked " + vehicle + " at " + slot + " | Ticket: " + ticket.getTicketId());
                return ticket;
            }
        }

        // Pass 2: flexible match
        for (ParkingFloor floor : floors) {
            ParkingSlot slot = floor.assignFlexibleSlot(vehicle.getType());
            if (slot != null) {
                Ticket ticket = new Ticket(vehicle, slot, entryTime);
                activeTickets.put(number, ticket);
                System.out.println("🅿️ Parked " + vehicle + " at " + slot + " | Ticket: " + ticket.getTicketId());
                return ticket;
            }
        }

        System.out.println("🚫 No available slot for " + vehicle.getType());
        return null;
    }

    /*
        Unpark a vehicle and process payment.

        synchronized to prevent double-unpark race.
        Time: O(1) for lookup + O(log n) for freeing slot
    */
    public synchronized Ticket unparkVehicle(String vehicleNumber, Wallet wallet) {
        Ticket ticket = activeTickets.get(vehicleNumber);
        if (ticket == null) {
            System.out.println("⚠️ No active ticket for vehicle " + vehicleNumber);
            return null;
        }

        boolean paid = paymentService.processPayment(ticket, wallet);

        // Free the slot regardless (vehicle is leaving)
        ParkingSlot slot = ticket.getSlot();
        for (ParkingFloor floor : floors) {
            if (floor.getFloorNumber() == slot.getFloorNumber()) {
                floor.freeSlot(slot);
                break;
            }
        }

        activeTickets.remove(vehicleNumber);
        System.out.println("🚗 Vehicle " + vehicleNumber + " has exited. Payment: " + (paid ? "PAID" : "UNPAID"));
        return ticket;
    }

    // Unpark with custom exit time (for testing)
    public synchronized Ticket unparkVehicle(String vehicleNumber, Wallet wallet, LocalDateTime exitTime) {
        Ticket ticket = activeTickets.get(vehicleNumber);
        if (ticket == null) {
            System.out.println("⚠️ No active ticket for vehicle " + vehicleNumber);
            return null;
        }

        boolean paid = paymentService.processPayment(ticket, wallet, exitTime);

        ParkingSlot slot = ticket.getSlot();
        for (ParkingFloor floor : floors) {
            if (floor.getFloorNumber() == slot.getFloorNumber()) {
                floor.freeSlot(slot);
                break;
            }
        }

        activeTickets.remove(vehicleNumber);
        System.out.println("🚗 Vehicle " + vehicleNumber + " has exited. Payment: " + (paid ? "PAID" : "UNPAID"));
        return ticket;
    }

    /*
        Search for a parked vehicle by its number.
        Time: O(1) — ConcurrentHashMap lookup (lock-free read)
    */
    public Ticket searchVehicle(String vehicleNumber) {
        return activeTickets.get(vehicleNumber);
    }

    public void displayAvailability() {
        System.out.println("\n=== " + name + " — Availability ===");
        for (ParkingFloor floor : floors) {
            System.out.println(floor);
        }
        System.out.println("Active vehicles: " + activeTickets.size());
        System.out.println();
    }

    public BlacklistService getBlacklistService() {
        return blacklistService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public String getName() {
        return name;
    }
}
