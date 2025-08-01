package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankTestEQV {
    // Testing for deposit function ()

    @Test
    void TestDeposit_Successful(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        assertTrue(bank.depositToAcc("testLogin", "chequing", 5000));
        assertEquals(5000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_InvalidUserNameFail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        assertFalse(bank.depositToAcc("guest", "chequing", 5000));
        assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_InvalidAccountNameFail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        assertFalse(bank.depositToAcc("testLogin", "investment", 5000));
        assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_NegativeAmountFail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        assertFalse(bank.depositToAcc("testLogin", "chequing", -20));
        assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestDeposit_TooMuchAmountFail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        assertFalse(bank.depositToAcc("testLogin", "chequing", 12000));
        assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    //@Test
    //void TestDeposit_InvalidAmountFail(){
      //  Bank bank = new Bank();
       // bank.addAccount("testLogin", "chequing");
      //  assertFalse(bank.depositToAcc("testLogin", "chequing", "abc"));
      //  assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
      //  bank.deleteAccount("testLogin", "chequing");
    //}

    // Test above is taken out due to depositToAcc only taking double argument for amount, therefore untestable in JUnit.

    // End Deposit Function Testing

    // Withdraw Function Testing

    @Test
    void TestWithdraw_Success(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertTrue(bank.withdrawFromAcc("testLogin", "chequing", 3000));
        assertEquals(7000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestWithdraw_InvalidUsernameFail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertFalse(bank.withdrawFromAcc("guest", "chequing", 3000));
        assertEquals(10000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestWithdraw_InvalidAccountTypeFail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertFalse(bank.withdrawFromAcc("guest", "investment", 3000));
        assertEquals(10000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestWithdraw_NegativeAmountFail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertFalse(bank.withdrawFromAcc("guest", "chequing", -20));
        assertEquals(10000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

    @Test
    void TestWithdraw_TooMuchAmountFail(){
        Bank bank = new Bank();
        bank.deleteAccount("testLogin", "chequing");
        bank.addAccount("testLogin", "chequing");
        bank.depositToAcc("testLogin", "chequing", 10000);
        assertFalse(bank.withdrawFromAcc("guest", "chequing", 7000));
        assertEquals(10000, bank.getAccount("testLogin", "chequing").getAccountBalance());
        bank.deleteAccount("testLogin", "chequing");
    }

   // @Test
    //void TestWithdraw_TooMuchAmountFail(){
      //  Bank bank = new Bank();
      // bank.addAccount("testLogin", "chequing");
        // bank.depositToAcc("testLogin", "chequing", 10000);
       // assertFalse(bank.withdrawFromAcc("guest", "chequing", "abc"));
      //  assertEquals(10000, bank.getAccount("testLogin", "chequing").getAccountBalance());
       // bank.deleteAccount("testLogin", "chequing");
   // }

 // Test above is taken out due to withdrawFromAcc only taking double argument for amount, therefore untestable in JUnit.

// End Withdraw Function Testing

// Transfer Function Testing

@Test
void TestTransfer_Success(){
    Bank bank = new Bank();
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
    bank.addAccount("testLogin", "chequing");
    bank.addAccount("testLogin", "savings");
    bank.depositToAcc("testLogin", "savings", 10000);
    assertTrue(bank.transferFunds("testLogin", "savings", "chequing", 3000));
    assertEquals(3000, bank.getAccount("testLogin", "chequing").getAccountBalance());
    assertEquals(7000, bank.getAccount("testLogin", "savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
}

@Test
void TestTransfer_InvalidUsernameFail(){
    Bank bank = new Bank();
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
    bank.addAccount("testLogin", "chequing");
    bank.addAccount("testLogin", "savings");
    bank.depositToAcc("testLogin", "savings", 10000);
    assertFalse(bank.transferFunds("guest", "savings", "chequing", 3000));
    assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
    assertEquals(10000, bank.getAccount("testLogin", "savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
}

@Test
void TestTransfer_InvalidFromAccountTypeFail(){
    Bank bank = new Bank();
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
    bank.addAccount("testLogin", "chequing");
    bank.addAccount("testLogin", "savings");
    bank.depositToAcc("testLogin", "savings", 10000);
    assertFalse(bank.transferFunds("testLogin", "investment", "chequing", 3000));
    assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
    assertEquals(10000, bank.getAccount("testLogin", "savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
}

@Test
void TestTransfer_InvalidtoAccountTypeFail(){
    Bank bank = new Bank();
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
    bank.addAccount("testLogin", "chequing");
    bank.addAccount("testLogin", "savings");
    bank.depositToAcc("testLogin", "savings", 10000);
    assertFalse(bank.transferFunds("testLogin", "savings", "investment", 3000));
    assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
    assertEquals(10000, bank.getAccount("testLogin", "savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
}

@Test
void TestTransfer_SameAccountFail(){
    Bank bank = new Bank();
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
    bank.addAccount("testLogin", "chequing");
    bank.addAccount("testLogin", "savings");
    bank.depositToAcc("testLogin", "savings", 10000);
    assertFalse(bank.transferFunds("testLogin", "savings", "savings", 3000));
    assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
    assertEquals(10000, bank.getAccount("testLogin", "savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
}

@Test
void TestTransfer_NegativeAmountFail(){
    Bank bank = new Bank();
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
    bank.addAccount("testLogin", "chequing");
    bank.addAccount("testLogin", "savings");
    bank.depositToAcc("testLogin", "savings", 10000);
    assertFalse(bank.transferFunds("testLogin", "savings", "savings", -20));
    assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
    assertEquals(10000, bank.getAccount("testLogin", "savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
}

@Test
void TestTransfer_TooMuchAmountFail(){
    Bank bank = new Bank();
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
    bank.addAccount("testLogin", "chequing");
    bank.addAccount("testLogin", "savings");
    bank.depositToAcc("testLogin", "savings", 10000);
    assertFalse(bank.transferFunds("testLogin", "savings", "savings", 8000));
    assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
    assertEquals(10000, bank.getAccount("testLogin", "savings").getAccountBalance());
    bank.deleteAccount("testLogin", "chequing");
    bank.deleteAccount("testLogin", "savings");
}

//@Test
//void TestTransfer_StringAmountFail(){
  //  Bank bank = new Bank();
    //bank.addAccount("testLogin", "chequing");
    //bank.addAccount("testLogin", "savings");
    //bank.depositToAcc("testLogin", "savings", 10000);
    //assertFalse(bank.transferFunds("testLogin", "savings", "savings", "abc"));
    //assertEquals(0, bank.getAccount("testLogin", "chequing").getAccountBalance());
    //assertEquals(10000, bank.getAccount("testLogin", "savings").getAccountBalance());
    //bank.deleteAccount("testLogin", "chequing");
    //bank.deleteAccount("testLogin", "savings");
//}

// Test above is commented out because transferFuns function can't take non double amount arguments therefore not testable in JUnit

// End Transfer Function Testing



}
