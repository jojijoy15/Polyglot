package com.problems.learning.system.parkinglot;

import java.time.LocalDateTime;
import java.util.UUID;

/*
    Ticket issued when a vehicle is parked.
    Tracks entry/exit time for fare calculation.
*/
public class Ticket {

    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot slot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private TicketStatus status;
    private double amountCharged;

    public Ticket(Vehicle vehicle, ParkingSlot slot) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
        this.amountCharged = 0.0;
    }

    // Constructor with custom entry time (for testing)
    public Ticket(Vehicle vehicle, ParkingSlot slot, LocalDateTime entryTime) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
        this.status = TicketStatus.ACTIVE;
        this.amountCharged = 0.0;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public double getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(double amountCharged) {
        this.amountCharged = amountCharged;
    }

    @Override
    public String toString() {
        return "Ticket[" + ticketId + "] " + vehicle + " → " + slot
                + " | Entry: " + entryTime + " | Status: " + status;
    }
}

