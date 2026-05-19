package com.problems.learning.system.parkinglot;

/*
    Wallet for a vehicle owner.
    Supports credit (top-up) and debit (payment).
*/
public class Wallet {

    private final String ownerId;
    private double balance;

    public Wallet(String ownerId, double initialBalance) {
        this.ownerId = ownerId;
        this.balance = initialBalance;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public double getBalance() {
        return balance;
    }

    public void credit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Credit amount must be positive");
        this.balance += amount;
    }

    public boolean debit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Debit amount must be positive");
        if (balance < amount) {
            return false; // insufficient funds
        }
        this.balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "Wallet[" + ownerId + "] Balance: ₹" + String.format("%.2f", balance);
    }
}

