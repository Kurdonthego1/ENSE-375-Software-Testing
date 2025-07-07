package com.example;

public class BankAccount {

    private String accountType;

    private BankUser accountOwner;

    private double accountBalance;

    public BankAccount(String accountType,BankUser accountOwner, double accountBalance){
        this.accountType = accountType;
        this.accountOwner = accountOwner;
        this.accountBalance = accountBalance;
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

    public void setAccountBalance(double newAccountBalance) {
        this.accountBalance = newAccountBalance;
    }

    
    
}
