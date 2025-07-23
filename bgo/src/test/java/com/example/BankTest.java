package com.example;

import java.sql.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BankTest {
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
}