package com.problems.learning.bankmanagementsystem;

import com.problems.learning.bankmanagementsystem.accounts.BankAccount;
import com.problems.learning.bankmanagementsystem.accounts.CurrentAccount;
import com.problems.learning.bankmanagementsystem.accounts.SavingsAccount;

public class BankingSystem {

    public static void main(String[] args) {
        BankAccount alice = new SavingsAccount("Alice", 5000, 0.05);
        BankAccount bob = new CurrentAccount("Bob", 5000, 0.05);
        alice.deposit(2000);
        ((SavingsAccount)alice).applyInterestRate();
        bob.withdraw(5500);
        alice.printBalance();
        bob.printBalance();
        BankAccount.compareBalances(alice, bob);
    }
}
