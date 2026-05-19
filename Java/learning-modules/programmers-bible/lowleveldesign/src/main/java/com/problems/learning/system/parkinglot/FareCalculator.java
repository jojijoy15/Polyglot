package com.problems.learning.system.parkinglot;

import java.time.Duration;
import java.time.LocalDateTime;

/*
    Calculates parking fare based on:
        1. Duration = ceiling of (exitTime - entryTime) in hours
        2. Rate = VehicleType.hourlyRate

    Partial hours are charged as full hours (ceiling-based).

    Example:
        CAR parked for 2 hours 15 minutes → charged for 3 hours → 3 × ₹20 = ₹60
*/
public class FareCalculator {

    public static double calculateFare(Ticket ticket) {
        LocalDateTime entry = ticket.getEntryTime();
        LocalDateTime exit = ticket.getExitTime();

        if (exit == null) {
            exit = LocalDateTime.now();
        }

        long minutes = Duration.between(entry, exit).toMinutes();
        // Ceiling: partial hour counts as full hour, minimum 1 hour
        long hours = Math.max(1, (minutes + 59) / 60); // same ceil trick as (p + k - 1) / k

        double rate = ticket.getVehicle().getType().getHourlyRate();
        return hours * rate;
    }
}

