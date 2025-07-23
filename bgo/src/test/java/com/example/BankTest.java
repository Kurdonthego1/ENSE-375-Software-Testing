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

        //Clean slate before test
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db")){
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM bankaccounts WHERE accountOwner = ? AND accountType = ?");
            deleteStmt.setString(1, "testLogin");
            deleteStmt.setString(2, "chequing");
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(bank.userlogin("testLogin", "1234"));
        assertTrue(bank.makeAccount("testLogin", "chequing"));

        BankAccount account = bank.getAccount("testLogin", "chequing");
        assertNotNull(account);

        boolean depositResult = bank.depositToAcc(account, 123.31);

        assertTrue(depositResult);
        
    }

    @Test
    public void testNegativeDeposit(){
        Bank bank = new Bank();

        //Clean slate before test
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db")){
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM bankaccounts WHERE accountOwner = ? AND accountType = ?");
            deleteStmt.setString(1, "testLogin");
            deleteStmt.setString(2, "chequing");
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(bank.userlogin("testLogin", "1234"));
        assertTrue(bank.makeAccount("testLogin", "chequing"));
        BankAccount account = bank.getAccount("testLogin", "chequing");
        assertNotNull(account);
        boolean depositResult = bank.depositToAcc(account, -34.30);
        assertFalse(depositResult);
    }
    
}