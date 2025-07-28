package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankTestDF {

    @Test
    void testAddAccountChequing(){ 
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        boolean result = bank.addAccount("testLogin","chequing");
        assertTrue(result);
    }

    @Test
    void testAccountThatsNotAnOption_Fail(){
        Bank bank = new Bank();
        boolean result = bank.addAccount("testLogin","Roblox");
        assertFalse(result);
    }

    @Test
    void testSQLErrorAddAccount_Fail(){
        Bank bank = new Bank("jdbc:sqlite:/invalid/path");
        boolean result = bank.addAccount("testLogin","chequing");
        assertFalse(result);
    }

    
}
