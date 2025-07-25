package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


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
    public void checkAccountBalances(){
        Bank bank = new Bank();
        boolean loginSuccess = bank.userlogin("testLogin", "1234");
        assertTrue(loginSuccess, "Login should be successful");
        BankAccount account = bank.getAccount("testLogin", "savings");
        assertNotNull(account, "Account should not be null");
        bank.checkAccountBalances("testLogin", "savings");
        assertEquals(0.00, account.getAccountBalance(), "Initial balance should be 0.00");
    }

    @Test
    public void checkAccountBalancesInvalidAccount() {
        Bank bank = new Bank();
        boolean loginSuccess = bank.userlogin("testLogin", "1234");
        assertTrue(loginSuccess, "Login should be successful");
        BankAccount account = bank.getAccount("testLogin", "investment");
        assertNull(account, "Account should be null for invalid account type");
        bank.checkAccountBalances("testLogin", "investment");
        assertNull(account, "Account should still be null for invalid account type");
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


    @Test
    void testDeleteAccountChequing(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin","chequing");
        boolean result = bank.deleteAccount("testLogin","chequing");
        assertTrue(result);
    }

    @Test
    void testDeleteNonExistingAccount_Fail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        boolean result = bank.deleteAccount("testLogin","chequing");
        assertFalse(result);
    }

    @Test
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

    
    //Withdraw Path Testing
    @Test
public void testSuccessfullWithdraw() {
    Bank bank = new Bank();
    bank.deleteAccount("testLogin", "chequing");
    bank.addAccount("testLogin", "chequing");
    BankAccount account = bank.getAccount("testLogin", "chequing");
    assertNotNull(account, "Account should not be null");
    bank.depositToAcc(account, 100.00);
    boolean withdrawResult = bank.withdrawFromAcc("testLogin", "chequing", 20.00);
    assertTrue(withdrawResult, "Withdrawal should succeed");
    BankAccount updatedAccount = bank.getAccount("testLogin", "chequing");
    assertEquals(80.00, updatedAccount.getAccountBalance(), 0.001, "Balance should be 80.00 after withdrawal");
}

    



    @Test
    public void testWithdrawalMoreThanBalance(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        BankAccount account = bank.getAccount("testLogin", "chequing");
        bank.depositToAcc(account, 100.00);
        boolean withdrawResult = bank.withdrawFromAcc("testLogin", "chequing", 110.00);
        assertFalse(withdrawResult, "Withdrawal should fail");
    }

    @Test
    public void testWithdrawalFromNonExistentAccount(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        boolean withdrawResult = bank.withdrawFromAcc("testLogin", "chequing", 110.00);
        assertFalse(withdrawResult, "Withdrawal should fail - no exist");
    }

    @Test
    public void testWithdrawalFromInvalidAccount(){
        Bank bank = new Bank();
        boolean withdrawResult = bank.withdrawFromAcc("testLogin", "investment", 110.00);
        assertFalse(withdrawResult, "Withdrawal should fail because investment doesnt exist");
    }

    // Transfer money between accounts test
    @Test
    public void testTransferSuccess() {
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.deleteAccount("testLogin", "savings");
        bank.addAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "savings");

        // deposit 400 into chequing
        BankAccount chequing1 = bank.getAccount("testLogin", "chequing");
        assertNotNull(chequing1);
        assertTrue(bank.depositToAcc(chequing1, 400.00));

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

        BankAccount chequing = bank.getAccount("testLogin", "chequing");
        assertTrue(bank.depositToAcc(chequing, 100.00));

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

        BankAccount chequing = bank.getAccount("testLogin", "chequing");
        assertNotNull(chequing);
        assertTrue(bank.depositToAcc(chequing, 100.00));


        //transfer
        boolean transferResult = bank.transferFunds("testLogin", "chequing", "savings", 250.00);
        assertFalse(transferResult, "Shouldnt allow transfer more than current balance");

        BankAccount chequing2 = bank.getAccount("testLogin", "chequing");
        BankAccount savings2 = bank.getAccount("testLogin", "savings");
        assertEquals(100.0, chequing2.getAccountBalance(), 0.01, "Chequing should stay at 100");
        assertEquals(0.00, savings2.getAccountBalance(), 0.01, "Savings should be 0 because we dont got 100 in the chequing");
    }


    
    
}

   