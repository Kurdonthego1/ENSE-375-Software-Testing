package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BankTest {
    //Testing User Registration
    // @Test
    // void TestValidUserRegistration() {
    //     Bank bank = new Bank();
    //     boolean userregistered = bank.usersignup("bob", "secretpassword");
    //     assertTrue(userregistered);
    // }

    // @Test
    // void TestInvalidUserRegistration() {
    //     Bank bank = new Bank();
    //     boolean userregistered = bank.usersignup("bob", "piss");
    //     assertFalse(userregistered);
    // }

    // Testing user login
    // @Test
    // void TestValidUserLogin() {
    //     Bank bank = new Bank();
    //     bank.usersignup("bob", "secretpassword");
    //     boolean userLoggedin = bank.userlogin("bob", "secretpassword");
    //     assertTrue(userLoggedin);
    // }

    // @Test
    // void TestInvalidUserLogin() {
    //     Bank bank = new Bank();
    //     bank.usersignup("bob", "idiot");
    //     boolean userLoggedin = bank.userlogin("bob", "idiot");
    //     assertFalse(userLoggedin);
    // }

    //Testing account creation (savings and chequing)
    // @Test
    // void TestMakingChequing(){
    //     Bank bank = new Bank();
    //     bank.usersignup("testacc1", "testpass1");
    //     boolean userLoggedin = bank.userlogin("testacc1", "testpass1");
    //     boolean accountMade = bank.makeAccount("testacc1", "chequing");
    //     assertTrue(accountMade);
    // }
    // @Test
    // void TestMakingSavings(){
    //     Bank bank = new Bank();
    //     bank.usersignup("testacc2", "testpass2");
    //     boolean userLoggedin = bank.userlogin("testacc2", "testpass2");
    //     boolean accountMade = bank.makeAccount("testacc2", "savings");
    //     assertTrue(accountMade);
    // }
    // @Test
    // void TestInvalidAccount() {
    // Bank bank = new Bank();
    // bank.usersignup("testacc3", "testpass3");
    // bank.userlogin("testacc3", "testpass3");
    // boolean accountMade = bank.makeAccount("testacc3", "Business");
    // assertFalse(accountMade, "Account creation with invalid type should fail");
    // }

    // @Test
    // void TestRetrieveAccount(){
    //     Bank bank = new Bank();
    //     bank.usersignup("testacc4", "testpass4");
    //     bank.userlogin("testacc4", "testpass4");
    //     bank.makeAccount("testacc4", "savings");
    //     BankAccount accountRetrieved = bank.getAccount("testacc4","savings");
    //     assertNotNull(accountRetrieved);
    // }
    
    //Depositing
    // @Test
    // void testDeposit(){
    //     Bank bank = new Bank();
    //     bank.usersignup("testacc5", "testpass5");
    //     bank.userlogin("testacc5", "testpass5");

    //     bank.makeAccount("testacc5", "chequing");
    //     BankAccount account = bank.getAccount("testacc5", "chequing");
    //     bank.depositToAcc(account, 150.00);

    //     assertEquals(150.00, bank.getAccount("testacc5", 
    //         "chequing").getAccountBalance()-(bank.getAccount("testacc5", 
    //         "chequing").getAccountBalance()-150), 0.01);
    // }


    //Withdrawing
    // @Test
    // void TestWithdrawFromAccount(){
    //     Bank bank = new Bank();
    //     bank.usersignup("testacc6", "testpass6");
    //     bank.userlogin("testacc6", "testpass6");
    //     bank.makeAccount("testacc6", "savings");
    //     BankAccount account = bank.getAccount("testacc6", "savings");
    //     bank.depositToAcc(account, 600);
    //     boolean result = bank.withdrawFromAcc("testacc6","savings",300.00);
    //     assertTrue(result);
    // }

    // @Test
    // void TestWithdrawFromAccountTooMuch(){
    //     Bank bank = new Bank();
    //     bank.usersignup("testacc7", "testpass7");
    //     bank.userlogin("testacc7", "testpass7");
    //     bank.makeAccount("testacc7", "savings");
    //     BankAccount account = bank.getAccount("testacc7", "savings");
    //     bank.depositToAcc(account, 500);
    //     boolean result = bank.withdrawFromAcc("testacc7","savings",600.00);
    //     assertTrue(result);
    // }

    // @Test
    // void TestWithdrawFromNonExistingAccount(){
    //     Bank bank = new Bank();
    //     bank.usersignup("testacc8", "testpass8");
    //     bank.userlogin("testacc8", "testpass8");
    //     bank.makeAccount("testacc8", "savings");
    //     BankAccount account = bank.getAccount("testacc8", "savings");
    //     bank.depositToAcc(account, 500);
    //     boolean result = bank.withdrawFromAcc("testacc8","chequing",600.00);
    //     assertFalse(result);
    // }
    
    //Balance inquiring
}