package com.example;

import java.sql.*;
import java.io.*;
import java.util.*;


public class Bank {

    private final String url;

    public Bank(){
        this.url = "jdbc:sqlite:bank.db";
        try (Connection conn = DriverManager.getConnection(url)){
        Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS bankusers (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS bankaccounts (id INTEGER PRIMARY KEY AUTOINCREMENT, accountType TEXT NOT NULL, accountOwner TEXT NOT NULL, accountBalance DOUBLE NOT NULL);");

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Login Failed. " + e.getMessage());
        }
    }

    public Bank(String customUrl){
        this.url = customUrl;
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
                return false;
                } else { 
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
            BankAccount accountBeingMade = new BankAccount(accountName, owner, 0.00, 0);
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
        BankAccount retrievedAccount = new BankAccount(rs.getString("accountType"),owner, rs.getDouble("accountBalance"), rs.getInt("id"));
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

public boolean depositToAcc(String username, String accountName, double amount){
        try (Connection conn = DriverManager.getConnection(url)){
            BankAccount account = getAccount(username, accountName);

            if (account == null){
            System.out.println("Failed to find account, please ensure this account exists.");
            return false;
            }
            if (amount <= 0 || amount > 10000 ) {
            System.out.println("Invalid deposit amount.");
            return false;
            }

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
                System.out.println("Can't withdraw more than the account Balance");
                return false;
            }
            if (withdrawAmount <= 0) {
                System.out.println("Please withdraw a value greather than 0");
                return false;            
            }
            if(withdrawAmount > 5000){
                System.out.println("You can only withdraw up to 5000");
                return false;
            }
             else {
                double newBalance = currentBalance - withdrawAmount;
                account.setAccountBalance(newBalance);
                PreparedStatement pstmt = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE id = ?;");
                pstmt.setDouble(1, newBalance);
                pstmt.setInt(2,account.getAccountId());
                int rowsUpdated = pstmt.executeUpdate();
                return rowsUpdated > 0;
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
                System.out.println("Failed to delete accounnt, please ensure the account exists and you type the correct account type");
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
    }
}

public void checkAccountBalances(String username, String accountType) {
        try (Connection conn = DriverManager.getConnection(url)) {
            BankAccount account = getAccount(username, accountType);
            if (account != null) {
                System.out.println("Account Type: " + account.getAccountType());
                System.out.println("Account Balance: " + account.getAccountBalance());
            } else {
                System.out.println("No account found for the specified type.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error checking account balances: " + e.getMessage());
        }
    }

    public boolean transferFunds(String username, String fromAccountType, String toAccountType, double amount){
        try (Connection conn = DriverManager.getConnection(url)) {
            BankAccount fromAcc = getAccount(username, fromAccountType);
            BankAccount toAcc = getAccount(username, toAccountType);

            if(fromAcc == null || toAcc == null){
                System.out.println("One of the accounts doesn't exist");
                return false;
            }
            if(amount <= 0){
                System.out.println("Transfer amount must be greather than 0");
                return false;
            }
            if(amount > 7500){
                System.out.println("Can only transfer up to 7500");
                return false;
            }
            if(fromAcc.getAccountType().equals(toAcc.getAccountType())){
                System.out.println("Can't transfer from the same account to the same account");
                return false;
            }
            double fromBalance = fromAcc.getAccountBalance();
            if(fromBalance < amount){
                return false;
            }

            double newFromBalance = fromBalance - amount;
            double newToBalance = toAcc.getAccountBalance() + amount;

            PreparedStatement updateFrom = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE id = ?;");
            updateFrom.setDouble(1, newFromBalance);
            updateFrom.setInt(2, fromAcc.getAccountId());
            int rows1 = updateFrom.executeUpdate();

            PreparedStatement updateTo = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE id = ?;");
            updateTo.setDouble(1, newToBalance);
            updateTo.setInt(2, toAcc.getAccountId());
            int rows2 = updateTo.executeUpdate();
            
            fromAcc.setAccountBalance(newFromBalance);
            toAcc.setAccountBalance(newToBalance);
            return rows1 > 0 && rows2 > 0;
            

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}