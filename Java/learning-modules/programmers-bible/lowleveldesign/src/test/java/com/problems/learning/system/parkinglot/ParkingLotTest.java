package com.problems.learning.system.parkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParkingLotTest {

    private ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        // 2 floors: each floor has 2 MOTORCYCLE, 2 CAR, 1 BUS slots
        Map<VehicleType, Integer> slotConfig = new LinkedHashMap<>();
        slotConfig.put(VehicleType.MOTORCYCLE, 2);
        slotConfig.put(VehicleType.CAR, 2);
        slotConfig.put(VehicleType.BUS, 1);

        ParkingFloor floor1 = new ParkingFloor(1, slotConfig);
        ParkingFloor floor2 = new ParkingFloor(2, slotConfig);

        parkingLot = new ParkingLot("City Mall Parking", List.of(floor1, floor2));
    }

    @Test
    void parkAndUnparkVehicle() {
        Vehicle car = new Vehicle("KA-01-1234", VehicleType.CAR);
        Wallet wallet = new Wallet("owner1", 500.0);

        Ticket ticket = parkingLot.parkVehicle(car);
        assertThat(ticket).isNotNull();
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.ACTIVE);

        // Search by vehicle number → O(1)
        Ticket found = parkingLot.searchVehicle("KA-01-1234");
        assertThat(found).isNotNull();
        assertThat(found.getTicketId()).isEqualTo(ticket.getTicketId());

        // Unpark
        Ticket exited = parkingLot.unparkVehicle("KA-01-1234", wallet);
        assertThat(exited).isNotNull();
        assertThat(exited.getStatus()).isEqualTo(TicketStatus.PAID);
        assertThat(exited.getAmountCharged()).isGreaterThan(0);
    }

    @Test
    void fareCalculationBasedOnHoursAndVehicleType() {
        Vehicle car = new Vehicle("KA-02-5678", VehicleType.CAR);
        Wallet wallet = new Wallet("owner2", 500.0);

        // Park 3 hours ago
        LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
        Ticket ticket = parkingLot.parkVehicle(car, threeHoursAgo);
        assertThat(ticket).isNotNull();

        // Unpark now → should charge 3 hours × ₹20 = ₹60
        parkingLot.unparkVehicle("KA-02-5678", wallet, LocalDateTime.now());
        assertThat(ticket.getAmountCharged()).isEqualTo(60.0);
        assertThat(wallet.getBalance()).isEqualTo(440.0);
    }

    @Test
    void differentFaresForDifferentVehicleTypes() {
        Vehicle bike = new Vehicle("KA-03-1111", VehicleType.MOTORCYCLE);
        Vehicle bus = new Vehicle("KA-03-2222", VehicleType.BUS);
        Wallet bikeWallet = new Wallet("biker", 200.0);
        Wallet busWallet = new Wallet("busDriver", 500.0);

        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);
        LocalDateTime now = LocalDateTime.now();

        parkingLot.parkVehicle(bike, twoHoursAgo);
        parkingLot.parkVehicle(bus, twoHoursAgo);

        Ticket bikeTicket = parkingLot.unparkVehicle("KA-03-1111", bikeWallet, now);
        Ticket busTicket = parkingLot.unparkVehicle("KA-03-2222", busWallet, now);

        // MOTORCYCLE: 2h × ₹10 = ₹20, BUS: 2h × ₹50 = ₹100
        assertThat(bikeTicket.getAmountCharged()).isEqualTo(20.0);
        assertThat(busTicket.getAmountCharged()).isEqualTo(100.0);
    }

    @Test
    void partialHourChargedAsFull() {
        Vehicle car = new Vehicle("KA-04-9999", VehicleType.CAR);
        Wallet wallet = new Wallet("owner4", 500.0);

        // Park 2 hours 15 minutes ago → should charge 3 hours
        LocalDateTime entry = LocalDateTime.now().minusHours(2).minusMinutes(15);
        parkingLot.parkVehicle(car, entry);

        parkingLot.unparkVehicle("KA-04-9999", wallet, LocalDateTime.now());
        Ticket ticket = parkingLot.searchVehicle("KA-04-9999");
        // Vehicle already unparked, but we can check wallet
        // 3 hours × ₹20 = ₹60
        assertThat(wallet.getBalance()).isEqualTo(440.0);
    }

    @Test
    void blacklistOnFailedPayment() {
        Vehicle car = new Vehicle("KA-05-0000", VehicleType.CAR);
        Wallet emptyWallet = new Wallet("broke", 0.0);

        parkingLot.parkVehicle(car);

        // Unpark with empty wallet → payment fails → blacklisted
        parkingLot.unparkVehicle("KA-05-0000", emptyWallet);
        assertThat(parkingLot.getBlacklistService().isBlacklisted("KA-05-0000")).isTrue();

        // Try to park again → denied
        Ticket denied = parkingLot.parkVehicle(car);
        assertThat(denied).isNull();
    }

    @Test
    void blacklistRemovedAfterPayment() {
        Vehicle car = new Vehicle("KA-06-1111", VehicleType.CAR);
        Wallet emptyWallet = new Wallet("broke", 0.0);
        Wallet fullWallet = new Wallet("broke", 500.0);

        parkingLot.parkVehicle(car);
        parkingLot.unparkVehicle("KA-06-1111", emptyWallet);
        assertThat(parkingLot.getBlacklistService().isBlacklisted("KA-06-1111")).isTrue();

        // Clear blacklist manually, park again with funds
        parkingLot.getBlacklistService().remove("KA-06-1111");
        parkingLot.parkVehicle(car);
        parkingLot.unparkVehicle("KA-06-1111", fullWallet);
        assertThat(parkingLot.getBlacklistService().isBlacklisted("KA-06-1111")).isFalse();
    }

    @Test
    void floorCapacityFullSpillsToNextFloor() {
        // Each floor has 2 CAR slots + 1 BUS slot → CAR can use BUS slot (flexibility)
        // Fill all CAR slots (4) and BUS slots (2) first
        for (int i = 1; i <= 4; i++) {
            assertThat(parkingLot.parkVehicle(new Vehicle("C" + i, VehicleType.CAR))).isNotNull();
        }
        // Fill BUS slots so CAR can't flex into them
        parkingLot.parkVehicle(new Vehicle("BUS-A", VehicleType.BUS));
        parkingLot.parkVehicle(new Vehicle("BUS-B", VehicleType.BUS));

        // Now a 5th car has no CAR or BUS slot → null
        assertThat(parkingLot.parkVehicle(new Vehicle("C5", VehicleType.CAR))).isNull();
    }

    @Test
    void duplicateVehicleRejected() {
        Vehicle car = new Vehicle("KA-07-DUP", VehicleType.CAR);
        parkingLot.parkVehicle(car);

        Ticket duplicate = parkingLot.parkVehicle(car);
        assertThat(duplicate).isNull();
    }

    @Test
    void searchNonExistentVehicle() {
        assertThat(parkingLot.searchVehicle("GHOST-CAR")).isNull();
    }

    @Test
    void unparkNonExistentVehicle() {
        Wallet wallet = new Wallet("nobody", 100.0);
        Ticket result = parkingLot.unparkVehicle("GHOST-CAR", wallet);
        assertThat(result).isNull();
        assertThat(wallet.getBalance()).isEqualTo(100.0); // no deduction
    }

    @Test
    void walletDebitAndCredit() {
        Wallet wallet = new Wallet("test", 100.0);
        assertThat(wallet.debit(30.0)).isTrue();
        assertThat(wallet.getBalance()).isEqualTo(70.0);

        assertThat(wallet.debit(80.0)).isFalse(); // insufficient
        assertThat(wallet.getBalance()).isEqualTo(70.0); // unchanged

        wallet.credit(50.0);
        assertThat(wallet.getBalance()).isEqualTo(120.0);
    }

    @Test
    void slotFreedAfterUnpark() {
        Vehicle car1 = new Vehicle("FREE-1", VehicleType.CAR);
        Vehicle car2 = new Vehicle("FREE-2", VehicleType.CAR);
        Vehicle car3 = new Vehicle("FREE-3", VehicleType.CAR);
        Wallet wallet = new Wallet("rich", 10000.0);

        parkingLot.parkVehicle(car1);
        parkingLot.parkVehicle(car2);
        parkingLot.parkVehicle(car3);

        // Unpark car1 → frees a slot
        parkingLot.unparkVehicle("FREE-1", wallet);

        // Now a new car should be able to park
        Vehicle car4 = new Vehicle("FREE-4", VehicleType.CAR);
        assertThat(parkingLot.parkVehicle(car4)).isNotNull();
    }

    // ===================== SLOT FLEXIBILITY TESTS =====================

    @Test
    void motorcycleUsesCarSlotWhenMotorcycleSlotsAreFull() {
        // Fill all motorcycle slots (2 per floor × 2 floors = 4)
        for (int i = 1; i <= 4; i++) {
            assertThat(parkingLot.parkVehicle(new Vehicle("BIKE-" + i, VehicleType.MOTORCYCLE))).isNotNull();
        }

        // 5th motorcycle should get a CAR slot (flexibility)
        Ticket flexTicket = parkingLot.parkVehicle(new Vehicle("BIKE-5", VehicleType.MOTORCYCLE));
        assertThat(flexTicket).isNotNull();
        assertThat(flexTicket.getSlot().getSupportedType()).isEqualTo(VehicleType.CAR);
    }

    @Test
    void busCannotUseCarOrMotorcycleSlot() {
        // Fill all BUS slots (1 per floor × 2 floors = 2)
        parkingLot.parkVehicle(new Vehicle("BUS-1", VehicleType.BUS));
        parkingLot.parkVehicle(new Vehicle("BUS-2", VehicleType.BUS));

        // 3rd bus → no BUS slots, and BUS can't fit in CAR/MOTORCYCLE → null
        Ticket denied = parkingLot.parkVehicle(new Vehicle("BUS-3", VehicleType.BUS));
        assertThat(denied).isNull();
    }

    // ===================== GATE TESTS =====================

    @Test
    void entryGateCanParkVehicle() {
        Gate entryGate = parkingLot.addGate(1, Gate.GateType.ENTRY);

        Vehicle car = new Vehicle("GATE-E1", VehicleType.CAR);
        Ticket ticket = entryGate.enter(car);
        assertThat(ticket).isNotNull();
        assertThat(parkingLot.searchVehicle("GATE-E1")).isNotNull();
    }

    @Test
    void exitGateCannotAcceptEntry() {
        Gate exitGate = parkingLot.addGate(2, Gate.GateType.EXIT);

        Vehicle car = new Vehicle("GATE-X1", VehicleType.CAR);
        Ticket ticket = exitGate.enter(car);
        assertThat(ticket).isNull();
    }

    @Test
    void entryGateCannotAcceptExit() {
        Gate entryGate = parkingLot.addGate(1, Gate.GateType.ENTRY);
        Wallet wallet = new Wallet("test", 100.0);

        Ticket ticket = entryGate.exit("SOME-CAR", wallet);
        assertThat(ticket).isNull();
    }

    @Test
    void dualGateHandlesBothEntryAndExit() {
        Gate dualGate = parkingLot.addGate(3, Gate.GateType.BOTH);
        Wallet wallet = new Wallet("dual", 500.0);

        Vehicle car = new Vehicle("DUAL-1", VehicleType.CAR);
        Ticket parked = dualGate.enter(car);
        assertThat(parked).isNotNull();

        Ticket exited = dualGate.exit("DUAL-1", wallet);
        assertThat(exited).isNotNull();
        assertThat(exited.getStatus()).isEqualTo(TicketStatus.PAID);
    }

    @Test
    void multipleGatesConcurrentParking() {
        Gate gate1 = parkingLot.addGate(1, Gate.GateType.ENTRY);
        Gate gate2 = parkingLot.addGate(2, Gate.GateType.ENTRY);

        // Both gates park simultaneously (simulated sequentially here, thread-safety tested by design)
        Ticket t1 = gate1.enter(new Vehicle("MG-1", VehicleType.CAR));
        Ticket t2 = gate2.enter(new Vehicle("MG-2", VehicleType.CAR));

        assertThat(t1).isNotNull();
        assertThat(t2).isNotNull();
        // Should be on different slots
        assertThat(t1.getSlot()).isNotEqualTo(t2.getSlot());
    }

    // ===================== CONCURRENCY TEST =====================

    @Test
    void concurrentParkingDoesNotDuplicateSlots() throws InterruptedException {
        // Attempt to park 10 cars from 10 threads — only 4 CAR slots exist
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        Ticket[] results = new Ticket[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> {
                results[idx] = parkingLot.parkVehicle(new Vehicle("CONC-" + idx, VehicleType.CAR));
            });
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        // Count successful parks — should be exactly 6 (2 CAR + 1 BUS slot per floor × 2 floors, with flexibility)
        long parked = java.util.Arrays.stream(results).filter(java.util.Objects::nonNull).count();
        assertThat(parked).isEqualTo(6);
    }
}
