package com.problems.learning.system.parkinglot;

import java.util.EnumMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
    Represents a single floor in the parking lot.

    Data Structure Choices:
        TreeSet<ParkingSlot> per VehicleType → O(log n) to assign (pollFirst) and free (add) a slot.
        Lowest-numbered slot is always assigned first (natural ordering via Comparable).

    Thread Safety:
        ReentrantReadWriteLock — multiple readers (availability checks) can proceed
        concurrently, but writes (assign/free) are exclusive.

    Slot Flexibility:
        If no slot is available for the exact vehicle type, we try to fit the vehicle
        in a larger slot type (e.g., MOTORCYCLE → CAR slot → BUS slot).
*/
public class ParkingFloor {

    private final int floorNumber;
    // Available (unoccupied) slots per vehicle type — TreeSet for quick assignment
    private final Map<VehicleType, TreeSet<ParkingSlot>> availableSlots;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ParkingFloor(int floorNumber, Map<VehicleType, Integer> slotCountByType) {
        this.floorNumber = floorNumber;
        this.availableSlots = new EnumMap<>(VehicleType.class);

        int slotCounter = 1;
        for (Map.Entry<VehicleType, Integer> entry : slotCountByType.entrySet()) {
            VehicleType type = entry.getKey();
            int count = entry.getValue();
            TreeSet<ParkingSlot> slots = new TreeSet<>();
            for (int i = 0; i < count; i++) {
                slots.add(new ParkingSlot(slotCounter++, floorNumber, type));
            }
            availableSlots.put(type, slots);
        }
    }

    /*
        Two-step slot assignment used by ParkingLot:
            1. assignExactSlot() — try exact type match only
            2. assignFlexibleSlot() — try larger slot types (fallback)

        ParkingLot does: exact on ALL floors first → then flexible on ALL floors.
        This ensures a MOTORCYCLE uses a MOTORCYCLE slot on Floor-2 before
        flexing into a CAR slot on Floor-1.
    */

    /*
        Assigns exact-type slot only. Returns null if no exact match on this floor.
        Time: O(log n), Thread-safe: write lock
    */
    public ParkingSlot assignExactSlot(VehicleType vehicleType) {
        lock.writeLock().lock();
        try {
            return pollFromType(vehicleType);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /*
        Assigns a larger compatible slot (flexibility).
        Called only after exact match fails on ALL floors.
        Time: O(log n), Thread-safe: write lock
    */
    public ParkingSlot assignFlexibleSlot(VehicleType vehicleType) {
        lock.writeLock().lock();
        try {
            for (VehicleType slotType : VehicleType.values()) {
                if (slotType != vehicleType && vehicleType.canFitIn(slotType)) {
                    ParkingSlot slot = pollFromType(slotType);
                    if (slot != null) {
                        System.out.println("↕️ Flexible assignment: " + vehicleType
                                + " parked in " + slotType + " slot on Floor-" + floorNumber);
                        return slot;
                    }
                }
            }
            return null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /*
        Convenience method: tries exact first, then flexible (single-floor use).
    */
    public ParkingSlot assignSlot(VehicleType vehicleType) {
        ParkingSlot slot = assignExactSlot(vehicleType);
        return (slot != null) ? slot : assignFlexibleSlot(vehicleType);
    }

    private ParkingSlot pollFromType(VehicleType type) {
        TreeSet<ParkingSlot> slots = availableSlots.get(type);
        if (slots == null || slots.isEmpty()) return null;
        ParkingSlot slot = slots.pollFirst();
        slot.occupy();
        return slot;
    }

    /*
        Frees a slot and adds it back to the available set.

        Time: O(log n) — TreeSet.add()
        Thread-safe: write lock
    */
    public void freeSlot(ParkingSlot slot) {
        lock.writeLock().lock();
        try {
            slot.free();
            TreeSet<ParkingSlot> slots = availableSlots.get(slot.getSupportedType());
            if (slots != null) {
                slots.add(slot);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean hasAvailableSlot(VehicleType type) {
        lock.readLock().lock();
        try {
            // Check exact type
            TreeSet<ParkingSlot> slots = availableSlots.get(type);
            if (slots != null && !slots.isEmpty()) return true;

            // Check flexible (larger) types
            for (VehicleType slotType : VehicleType.values()) {
                if (slotType != type && type.canFitIn(slotType)) {
                    slots = availableSlots.get(slotType);
                    if (slots != null && !slots.isEmpty()) return true;
                }
            }
            return false;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getAvailableCount(VehicleType type) {
        lock.readLock().lock();
        try {
            TreeSet<ParkingSlot> slots = availableSlots.get(type);
            return (slots == null) ? 0 : slots.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public String toString() {
        lock.readLock().lock();
        try {
            StringBuilder sb = new StringBuilder("Floor-" + floorNumber + " availability: ");
            availableSlots.forEach((type, slots) ->
                    sb.append(type).append("=").append(slots.size()).append(" "));
            return sb.toString().trim();
        } finally {
            lock.readLock().unlock();
        }
    }
}
