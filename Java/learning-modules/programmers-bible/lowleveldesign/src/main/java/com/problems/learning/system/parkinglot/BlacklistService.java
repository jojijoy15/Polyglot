package com.problems.learning.system.parkinglot;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
    Manages a blacklist of vehicle numbers.

    Vehicles are blacklisted when they leave without paying.
    Blacklisted vehicles are denied entry until dues are cleared.

    Data Structure: ConcurrentHashMap.newKeySet() → O(1) thread-safe add, remove, contains.
*/
public class BlacklistService {

    private final Set<String> blacklistedVehicles = ConcurrentHashMap.newKeySet();

    public void blacklist(String vehicleNumber) {
        blacklistedVehicles.add(vehicleNumber);
        System.out.println("⛔ Vehicle " + vehicleNumber + " has been BLACKLISTED.");
    }

    public void remove(String vehicleNumber) {
        blacklistedVehicles.remove(vehicleNumber);
        System.out.println("✅ Vehicle " + vehicleNumber + " removed from blacklist.");
    }

    public boolean isBlacklisted(String vehicleNumber) {
        return blacklistedVehicles.contains(vehicleNumber);
    }

    public Set<String> getAll() {
        return Set.copyOf(blacklistedVehicles);
    }
}
