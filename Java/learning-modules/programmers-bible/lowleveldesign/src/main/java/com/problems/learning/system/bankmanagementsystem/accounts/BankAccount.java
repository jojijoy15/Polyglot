package com.problems.learning.system.bankmanagementsystem.accounts;

public abstract class BankAccount {

    private static int totalBankAccounts = 0;
    private int accountNumber;
    protected double balance;
    protected String accountHolderName;

    public BankAccount(String accountHolderName, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = totalBankAccounts++;
        this.balance = balance;
    }

    public abstract void withdraw(double amount);

    public void deposit(double amount) {
        balance = balance + amount;
        String balanceStatement = "Account Number '%d' has deposited '%f' and has balance '%f'\r\n";
        System.out.printf(balanceStatement, accountNumber, amount, balance);
    }

    public void printBalance() {
        String balanceStatement = "Account Number '%d' has Balance '%f'\r\n";
        System.out.printf(balanceStatement, accountNumber, balance);
    }

    public static void compareBalances(BankAccount firstAccount, BankAccount secondAccount) {
        String statement =  "'%s' has higher Balance than '%s'\r\n";
        if(firstAccount.balance > secondAccount.balance) {
            System.out.printf(statement, firstAccount.accountHolderName, secondAccount.accountHolderName);
        } else {
            System.out.printf(statement, secondAccount.accountHolderName, firstAccount.accountHolderName);
        }
    }

    protected int getAccountNumber() {
        return accountNumber;
    }

}
