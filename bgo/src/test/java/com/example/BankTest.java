package com.example;

import java.sql.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BankTest {
    private Bank bank;
    private String testUsername = "testLogin";
    private String testPassword = "1234";
    private String testAccountType = "savings";
    private String testAccountType2 = "chequing";
    private BankUser testUser;
    private BankAccount testAccount;
    private BankAccount testAccount2;

    @Test
    public void checkAccountBalances(username, accountType){
        Bank bank = new Bank();
        boolean loginSuccess = bank.userlogin("testLogin", "1234");
        assertTrue(loginSuccess, "Login should be successful");

        BankAccount account = bank.getAccount("testLogin", "savings");
        BankAccount account2 = bank.getAccount("testLogin", "chequing");
        assertNotNull(account, "Savings account should not be null");
        assertNotNull(account2, "Chequing account should not be null");

        assertEquals(0.00, account.getAccountBalance(), "Initial savings account balance should be 0.00");
        assertEquals(0.00, account2.getAccountBalance(), "Initial chequing account balance should be 0.00");
        
        account.setAccountBalance(100.00);
        account2.setAccountBalance(200.00);
        
        assertEquals(100.00, account.getAccountBalance(), "Savings account balance should be 100.00 after deposit");
        assertEquals(200.00, account2.getAccountBalance(), "Chequing account balance should be 200.00 after deposit");
        bank.closeConnection();
    }

    @Test
    public void checkAccountBalancesInvalidAccount() {
        Bank bank = new Bank();
        boolean loginSuccess = bank.userlogin("testLogin", "1234");
        assertTrue(loginSuccess, "Login should be successful");
        BankAccount account = bank.getAccount("testLogin", "investment");
        assertNull(account, "Account should be null for invalid account type");
        bank.closeConnection();
    }

    @Test
    public void checkAccountBalances(){

    }


    @Test
    void testAddAccountSavings(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "savings");
        boolean result = bank.addAccount("testLogin","savings");
        assertTrue(result);
    }

    @Test
    void testAddAccountChequing(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        boolean result = bank.addAccount("testLogin","chequing");
        assertTrue(result);
    }

    @Test
    void testAddAccountChequingAndSavings(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.deleteAccount("testLogin", "savings");
        boolean result = bank.addAccount("testLogin","chequing");
        boolean result2 = bank.addAccount("testLogin","savings");
        assertTrue(result && result2);
    }

    @Test
    void testAddAccountChequingTwice_Fail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin","chequing");
        boolean result = bank.addAccount("testLogin","chequing");
        assertFalse(result);
    }

    @Test
    void testAccountThatsNotAnOption_Fail(){
        Bank bank = new Bank();
        boolean result = bank.addAccount("testLogin","Roblox");
        assertFalse(result);
    }

    @Test
    void testDeleteAccountSavings(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "savings");
        bank.addAccount("testLogin","savings");
        boolean result = bank.deleteAccount("testLogin","savings");
        assertTrue(result);
    }

    void testDeleteAccountChequing(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin","chequing");
        boolean result = bank.deleteAccount("testLogin","chequing");
        assertTrue(result);
    }

    void testDeleteNonExistingAccount_Fail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        boolean result = bank.deleteAccount("testLogin","chequing");
        assertFalse(result);
    }

    void testDeleteNonAvailableAccount_Fail(){
        Bank bank = new Bank();
        boolean result = bank.deleteAccount("testLogin","roblox");
        assertFalse(result);
    }


    @Test
    public void testSuccessfulDeposit(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        BankAccount account = bank.getAccount("testLogin", "chequing");
        assertNotNull(account);
        boolean result = bank.depositToAcc(account, 234.21);
        assertTrue(result);
    }

    @Test
    public void testNegativeDeposit(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        BankAccount account = bank.getAccount("testLogin", "chequing");
        assertNotNull(account);
        boolean result = bank.depositToAcc(account, -43.21);
        assertFalse(result);
    }
}

   