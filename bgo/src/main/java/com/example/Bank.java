package com.example;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Bank {

    private static final String url = "jdbc:sqlite:bank.db";

    public Bank(){
        try (Connection conn = DriverManager.getConnection(url)){
        Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS bankusers (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS bankaccounts (id INTEGER PRIMARY KEY AUTOINCREMENT, accountType TEXT NOT NULL, accountOwner TEXT NOT NULL, accountBalance DOUBLE NOT NULL);");

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Login Failed. " + e.getMessage());
        }
    }
    
    // Make changes
    public boolean userlogin(String username, String password) { 
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bankusers WHERE username = ? AND password = ?;");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs =  pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("login failed, fookin dumbass");
                return false;
                } else { 
                    System.out.println("login successful, welcome fooker" + username);
                    return true;
                }
            } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Login Failed. " + e.getMessage());
            return false;
        }
    }

    public boolean addAccount(String username, String accountName){
        try(Connection conn = DriverManager.getConnection(url)){
           PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bankusers WHERE username = ?;");
           pstmt.setString(1,username); 
            ResultSet rs = pstmt.executeQuery();
            BankUser owner = new BankUser(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            BankAccount exists = getAccount(username, accountName);
            
        if(exists == null && (accountName.equals("chequing") || accountName.equals("savings"))){
            BankAccount accountBeingMade = new BankAccount(accountName, owner, 0.00);
            PreparedStatement insrt = conn.prepareStatement("INSERT INTO bankaccounts (accountType, accountOwner,accountBalance) VALUES(?,?,?);");
            insrt.setString(1, accountBeingMade.getAccountType());
            insrt.setString(2, accountBeingMade.getAccountOwner().getUsername());
            insrt.setDouble(3,accountBeingMade.getAccountBalance());
            insrt.executeUpdate();
            return true;
        }
        else{
            return false;
        }


        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public BankAccount getAccount(String username, String accountType){
        try(Connection conn = DriverManager.getConnection(url)){
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from bankaccounts WHERE accountOwner = ? AND accountType = ?;");
        pstmt.setString(1, username);
        pstmt.setString(2,accountType);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
        PreparedStatement pstmt2 = conn.prepareStatement("SELECT * FROM bankusers WHERE username = ?;");
        pstmt2.setString(1,username);
        ResultSet rs2 = pstmt2.executeQuery();
        if(rs2.next()){
        BankUser owner = new BankUser(rs2.getInt("id"),rs2.getString("username"),rs2.getString("password"));
        BankAccount retrievedAccount = new BankAccount(rs.getString("accountType"),owner, rs.getDouble("accountBalance"));
        return retrievedAccount;
        }
        else{
            return null;
        }

        }else {
            return null;
        }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;

    }

}

public boolean depositToAcc(BankAccount account, double amount){
        if (account == null){
            System.out.println("Cannot deposit.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return false;
        }

        try (Connection conn = DriverManager.getConnection(url)){
            double newBalance = account.getAccountBalance() + amount;
            PreparedStatement pstmt = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE accountOwner = ? AND accountType = ?;");

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, account.getAccountOwner().getUsername());
            pstmt.setString(3, account.getAccountType());
            pstmt.executeUpdate();
            account.setAccountBalance(newBalance);
            System.out.println("Deposit successful. Balance: " + account.getAccountBalance());
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Deposit Failed. " + e.getMessage());
            return false;
        }
    }

    public boolean withdrawFromAcc(String username, String accountType, double withdrawAmount){
        try(Connection conn = DriverManager.getConnection(url)){
            BankAccount account = getAccount(username, accountType);
            if(account == null){
                return false;
            }
            double currentBalance = account.getAccountBalance();
            if(withdrawAmount > currentBalance){
                return false;
            } else{
                double newBalance = currentBalance - withdrawAmount;
                account.setAccountBalance(newBalance);
                PreparedStatement pstmt = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE id = ?;");
                pstmt.setDouble(1, newBalance);
                pstmt.setInt(2,account.getAccountId());
                return true;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;

    }
    }

    public boolean deleteAccount(String username, String accountType){
        try(Connection conn = DriverManager.getConnection(url)){
            BankAccount accountExists = getAccount(username, accountType);
            if(accountExists != null){
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bankaccounts where accountOwner = ? AND accountType = ?");
                pstmt.setString(1,username);
                pstmt.setString(2, accountType);
                pstmt.executeUpdate();
                System.out.println(accountType + " account deleted succesfully");
                return true;
            }
            else{
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
    }
}
}