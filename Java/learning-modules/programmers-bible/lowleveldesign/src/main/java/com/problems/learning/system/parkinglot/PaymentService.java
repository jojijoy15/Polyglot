 package com.problems.learning.system.parkinglot;

import java.time.LocalDateTime;

/*
    Handles payment processing using a Wallet.

    Flow:
        1. Calculate fare from ticket
        2. Debit wallet
        3. If successful → mark ticket PAID
        4. If insufficient → mark UNPAID → blacklist the vehicle
*/
public class PaymentService {

    private final BlacklistService blacklistService;

    public PaymentService(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public boolean processPayment(Ticket ticket, Wallet wallet) {
        ticket.setExitTime(LocalDateTime.now());
        double fare = FareCalculator.calculateFare(ticket);
        ticket.setAmountCharged(fare);

        System.out.println("💰 Fare for " + ticket.getVehicle() + ": ₹" + String.format("%.2f", fare));

        boolean success = wallet.debit(fare);
        if (success) {
            ticket.setStatus(TicketStatus.PAID);
            System.out.println("✅ Payment successful. " + wallet);
            // If they were blacklisted before and cleared dues, remove from blacklist
            String vehicleNumber = ticket.getVehicle().getVehicleNumber();
            if (blacklistService.isBlacklisted(vehicleNumber)) {
                blacklistService.remove(vehicleNumber);
            }
        } else {
            ticket.setStatus(TicketStatus.UNPAID);
            blacklistService.blacklist(ticket.getVehicle().getVehicleNumber());
            System.out.println("❌ Payment failed. Insufficient balance. " + wallet);
        }
        return success;
    }

    // Process payment with custom exit time (for testing)
    public boolean processPayment(Ticket ticket, Wallet wallet, LocalDateTime exitTime) {
        ticket.setExitTime(exitTime);
        double fare = FareCalculator.calculateFare(ticket);
        ticket.setAmountCharged(fare);

        System.out.println("💰 Fare for " + ticket.getVehicle() + ": ₹" + String.format("%.2f", fare));

        boolean success = wallet.debit(fare);
        if (success) {
            ticket.setStatus(TicketStatus.PAID);
            System.out.println("✅ Payment successful. " + wallet);
            String vehicleNumber = ticket.getVehicle().getVehicleNumber();
            if (blacklistService.isBlacklisted(vehicleNumber)) {
                blacklistService.remove(vehicleNumber);
            }
        } else {
            ticket.setStatus(TicketStatus.UNPAID);
            blacklistService.blacklist(ticket.getVehicle().getVehicleNumber());
            System.out.println("❌ Payment failed. Insufficient balance. " + wallet);
        }
        return success;
    }
}

