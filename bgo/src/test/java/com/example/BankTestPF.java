package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankTestPF {

//We've selected the withdrawal function for path testing
    @Test
    public void testWithdrawalMoreThanBalance(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        BankAccount account = bank.getAccount("testLogin", "chequing");
        bank.depositToAcc("testLogin","chequing", 100.00);
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

    @Test
    public void testNegativeWithdrawal(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        BankAccount account = bank.getAccount("testLogin", "chequing");
        assertNotNull(account);
        boolean result = bank.withdrawFromAcc("testLogin", "chequing", -50.0);
        assertFalse(result);
    }

    @Test
    public void testWithdrawSQLException() {
    String url = "jdbc:sqlite:/this/path/does/not/exist.db";
    Bank bank = new Bank(url);
    boolean result = bank.withdrawFromAcc("testLogin", "chequing", 10.0);
    assertFalse(result, 
      "With an invalid URL we should catch SQLException and return false");
    }
    
}
