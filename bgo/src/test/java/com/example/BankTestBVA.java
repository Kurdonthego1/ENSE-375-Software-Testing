package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


public class BankTestBVA {

    //Deposit Function Testing

    @Test
    void TestDeposit_MinMinusFail(){
        Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        assertFalse(bank.depositToAcc("testLogin", "chequing", 0));
        assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

     @Test
    void TestDeposit_Min(){
        Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        assertTrue(bank.depositToAcc("testLogin", "chequing", 1));
        assertEquals(1, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_MinPlus(){
        Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        assertTrue(bank.depositToAcc("testLogin", "chequing", 2));
        assertEquals(2, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_Nom(){
        Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        assertTrue(bank.depositToAcc("testLogin", "chequing", 5000));
        assertEquals(5000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_MaxMinus(){
        Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        assertTrue(bank.depositToAcc("testLogin", "chequing", 9999));
        assertEquals(9999, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_Max(){
        Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        assertTrue(bank.depositToAcc("testLogin", "chequing", 10000));
        assertEquals(10000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_MaxPlusFail(){
        Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        assertFalse(bank.depositToAcc("testLogin", "chequing", 10001));
        assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }


  // End Deposit Function Test

  // Withdraw Function Test

  @Test
  void TestWithdraw_MinMinusFail(){
    Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertFalse(bank.withdrawFromAcc("testLogin", "chequing", 0));
        assertEquals(10000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
  }

   @Test
    void TestWithdraw_Min(){
    Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertTrue(bank.withdrawFromAcc("testLogin", "chequing", 1));
        assertEquals(9999, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
  }

  @Test
    void TestWithdraw_MinPlus(){
    Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertTrue(bank.withdrawFromAcc("testLogin", "chequing", 2));
        assertEquals(9998, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
  }

  @Test
    void TestWithdraw_Nom(){
    Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertTrue(bank.withdrawFromAcc("testLogin", "chequing", 2500));
        assertEquals(7500, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
  }

  @Test
    void TestWithdraw_MaxMinus(){
    Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertTrue(bank.withdrawFromAcc("testLogin", "chequing", 4999));
        assertEquals(5001, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
  }

  @Test
    void TestWithdraw_Max(){
    Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertTrue(bank.withdrawFromAcc("testLogin", "chequing", 5000));
        assertEquals(5000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
  }

  @Test
    void TestWithdraw_MaxPlusFail(){
    Bank bank = new Bank();
        bank.addAccount("testLogin","chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertFalse(bank.withdrawFromAcc("testLogin", "chequing", 5001));
        assertEquals(10000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
  }

  // End testing for Withdraw Function

  // Begin Testing for Transfer Function

  @Test
  void TestTransfer_MinMinusFail(){
    Bank bank = new Bank();
    bank.addAccount("testLogin","chequing");
    bank.depositToAcc("testLogin", "chequing", 10000);
    bank.addAccount("testLogin","savings");
    assertFalse(bank.transferFunds("testLogin", "chequing", "savings", 0));
    assertEquals(10000, bank.getAccount("testLogin","chequing").getAccountBalance());
    assertEquals(0, bank.getAccount("testLogin","savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
  }

  @Test
  void TestTransfer_Min(){
    Bank bank = new Bank();
    bank.addAccount("testLogin","chequing");
    bank.depositToAcc("testLogin", "chequing", 10000);
    bank.addAccount("testLogin","savings");
    assertTrue(bank.transferFunds("testLogin", "chequing", "savings", 1));
    assertEquals(9999, bank.getAccount("testLogin","chequing").getAccountBalance());
    assertEquals(1, bank.getAccount("testLogin","savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
  }

  @Test
  void TestTransfer_MinPlus(){
    Bank bank = new Bank();
    bank.addAccount("testLogin","chequing");
    bank.depositToAcc("testLogin", "chequing", 10000);
    bank.addAccount("testLogin","savings");
    assertTrue(bank.transferFunds("testLogin", "chequing", "savings", 2));
    assertEquals(9998, bank.getAccount("testLogin","chequing").getAccountBalance());
    assertEquals(2, bank.getAccount("testLogin","savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
  }

  @Test
  void TestTransfer_Nom(){
    Bank bank = new Bank();
    bank.addAccount("testLogin","chequing");
    bank.depositToAcc("testLogin", "chequing", 10000);
    bank.addAccount("testLogin","savings");
    assertTrue(bank.transferFunds("testLogin", "chequing", "savings", 3750));
    assertEquals(6250, bank.getAccount("testLogin","chequing").getAccountBalance());
    assertEquals(3750, bank.getAccount("testLogin","savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
  }

  @Test
  void TestTransfer_MaxMinus(){
    Bank bank = new Bank();
    bank.addAccount("testLogin","chequing");
    bank.depositToAcc("testLogin", "chequing", 10000);
    bank.addAccount("testLogin","savings");
    assertTrue(bank.transferFunds("testLogin", "chequing", "savings", 7499));
    assertEquals(2501, bank.getAccount("testLogin","chequing").getAccountBalance());
    assertEquals(7499, bank.getAccount("testLogin","savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
  }

  @Test
  void TestTransfer_Max(){
    Bank bank = new Bank();
    bank.addAccount("testLogin","chequing");
    bank.depositToAcc("testLogin", "chequing", 10000);
    bank.addAccount("testLogin","savings");
    assertTrue(bank.transferFunds("testLogin", "chequing", "savings", 7500));
    assertEquals(2500, bank.getAccount("testLogin","chequing").getAccountBalance());
    assertEquals(7500, bank.getAccount("testLogin","savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
  }

  @Test
  void TestTransfer_MaxPlusFail(){
    Bank bank = new Bank();
    bank.addAccount("testLogin","chequing");
    bank.depositToAcc("testLogin", "chequing", 10000);
    bank.addAccount("testLogin","savings");
    assertFalse(bank.transferFunds("testLogin", "chequing", "savings", 7501));
    assertEquals(10000, bank.getAccount("testLogin","chequing").getAccountBalance());
    assertEquals(0, bank.getAccount("testLogin","savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
  }

  // End Transfer Function Testing

}
