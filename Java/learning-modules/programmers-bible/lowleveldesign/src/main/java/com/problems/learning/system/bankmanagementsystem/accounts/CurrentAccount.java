package com.problems.learning.system.bankmanagementsystem.accounts;

public class CurrentAccount extends BankAccount {

    private double overDraftLimit;

    public CurrentAccount(String accountHolderName, double balance, double overDraftLimit) {
        super(accountHolderName, balance);
        this.overDraftLimit = overDraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        double overDraftLimit = this.overDraftLimit * balance;
        if( balance - amount > overDraftLimit ) {
            throw new IllegalArgumentException("Not enough balance after overDraft limit");
        }
        balance = balance - amount;
    }

}
