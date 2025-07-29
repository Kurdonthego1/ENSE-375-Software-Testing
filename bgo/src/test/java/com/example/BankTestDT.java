package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class BankTestDT {
    // Testing for transferfunds
    // Transfer money between accounts test
    @Test
    public void testTransferSuccess() {
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.deleteAccount("testLogin", "savings");
        bank.addAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "savings");

        // deposit 400 into chequing
        assertTrue(bank.depositToAcc("testLogin","chequing", 400.00));

        // do the transfer
        assertTrue(bank.transferFunds("testLogin", "chequing", "savings", 100.0));

        // re‑read both accounts from the DB
        BankAccount chequing2 = bank.getAccount("testLogin", "chequing");
        BankAccount savings2  = bank.getAccount("testLogin", "savings");
        assertEquals(300.00, chequing2.getAccountBalance(),0.01,"Chequing should be 300 after moving 100 out");
        assertEquals(100.00, savings2.getAccountBalance(),0.01,"Savings should be 100 after receiving 100");
    }
    
    @Test
    public void testTransferFromNonexistentAccount() {
        Bank bank = new Bank();
        // no accounts created at all
        assertFalse(bank.transferFunds("bob", "chequing", "savings", 10.00));
    }
    @Test
    public void testTransferInvalidAmount() {
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.deleteAccount("testLogin", "savings");
        bank.addAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "savings");

        assertTrue(bank.depositToAcc("testLogin","chequing", 100.00));

        //negative transfer
        assertFalse(bank.transferFunds("testLogin", "chequing", "savings", -5.00));
    }
    
    @Test
    public void testTransferInsufficient(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.deleteAccount("testLogin", "savings");
        bank.addAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "savings");

        assertTrue(bank.depositToAcc("testLogin","chequing", 100.00));


        //transfer
        boolean transferResult = bank.transferFunds("testLogin", "chequing", "savings", 250.00);
        assertFalse(transferResult, "Shouldnt allow transfer more than current balance");

        BankAccount chequing2 = bank.getAccount("testLogin", "chequing");
        BankAccount savings2 = bank.getAccount("testLogin", "savings");
        assertEquals(100.0, chequing2.getAccountBalance(), 0.01, "Chequing should stay at 100");
        assertEquals(0.00, savings2.getAccountBalance(), 0.01, "Savings should be 0 because we dont got 100 in the chequing");
    }

    @Test
    public void testTransferSameAccount(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");

        assertTrue(bank.depositToAcc("testLogin","chequing", 100.00));

        boolean transferResult = bank.transferFunds("testLogin", "chequing", "chequing", 50.00);
        assertFalse(transferResult, "Cannot transfer to the same account");
    }
}
