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
        bank.usersignup("bob", "idiot");
        boolean userLoggedin = bank.userlogin("bob", "idiot");
        assertFalse(userLoggedin);
    }

    //Testing account creation (savings and chequing)
    @Test
    void TestMakingChequing(){
        Bank bank = new Bank();
        bank.usersignup("testacc1", "testpass1");
        boolean userLoggedin = bank.userlogin("testacc1", "testpass1");
        boolean accountMade = bank.makeAccount("testacc1", "chequing");
        assertTrue(accountMade);
    }
    @Test
    void TestMakingSavings(){
        Bank bank = new Bank();
        bank.usersignup("testacc2", "testpass2");
        boolean userLoggedin = bank.userlogin("testacc2", "testpass2");
        boolean accountMade = bank.makeAccount("testacc2", "savings");
        assertTrue(accountMade);
    }
    @Test
    void TestInvalidAccount() {
    Bank bank = new Bank();
    bank.usersignup("testacc3", "testpass3");
    bank.userlogin("testacc3", "testpass3");
    boolean accountMade = bank.makeAccount("testacc3", "Business");
    assertFalse(accountMade, "Account creation with invalid type should fail");
    }



    //Depositing
    
    //Withdrawing


    //Balance inquiring

    
    
}
