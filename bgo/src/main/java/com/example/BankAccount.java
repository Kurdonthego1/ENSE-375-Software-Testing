package com.example;

public class BankAccount {

    private String accountType;

    private BankUser accountOwner;

    private double accountBalance;

    private int accountId;

    public BankAccount(String accountType,BankUser accountOwner, double accountBalance, int accountId){
        this.accountType = accountType;
        this.accountOwner = accountOwner;
        this.accountBalance = accountBalance;
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public BankUser getAccountOwner() {
        return accountOwner;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountBalance(double newAccountBalance) {
        this.accountBalance = newAccountBalance;
    }
}
