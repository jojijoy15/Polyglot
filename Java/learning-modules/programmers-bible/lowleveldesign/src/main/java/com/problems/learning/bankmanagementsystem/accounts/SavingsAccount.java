package com.problems.learning.bankmanagementsystem.accounts;

public class SavingsAccount extends BankAccount {

    private double interestRate;

    public SavingsAccount(String accountHolderName, double balance,  double interestRate) {
        super(accountHolderName, balance);
        this.interestRate = interestRate;
    }

    public void applyInterestRate() {
        balance *= (1 + interestRate);
        String balanceStatement = "Account Number '%d' has interest rate '%f' applied and has balance '%f'\r\n";
        System.out.printf(balanceStatement, getAccountNumber(), interestRate, balance);
    }

    @Override
    public void withdraw(double amount) {
        if( balance - amount < 0 ) {
            throw new IllegalArgumentException("Not enough balance");
        }
        balance = balance - amount;
        String balanceStatement = "Account Number '%d' has withdrawn '%f' and has balance '%f'\r\n";
        System.out.printf(balanceStatement, getAccountNumber(), amount, balance);
    }
}
