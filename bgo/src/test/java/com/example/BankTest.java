package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public class BankTest {
    //Testing User Registration
    @Test
    void TestValidUserRegistration() {
        Bank bank = new Bank();
        boolean userregistered = bank.usersignup("bob", "secretpassword");
        assertTrue(userregistered);
    }

    @Test
    void TestInvalidUserRegistration() {
        Bank bank = new Bank();
        boolean userregistered = bank.usersignup("bob", "piss");
        assertFalse(userregistered);
    }

    //Testing user login
    @Test
    void TestValidUserLogin() {
        Bank bank = new Bank();
        bank.usersignup("bob", "secretpassword");
        boolean userLoggedin = bank.userlogin("bob", "secretpassword");
        assertTrue(userLoggedin);
    }

    @Test
    void TestInvalidUserLogin() {
        Bank bank = new Bank();
        bank.usersignup("bob", "piss");
        boolean userLoggedin = bank.userlogin("bob", "piss");
        assertFalse(userLoggedin);
    }


    //Testing account creation (savings and chequing)
 
    //Depositing
    
    //Withdrawing


    //Balance inquiring

    
    
}
