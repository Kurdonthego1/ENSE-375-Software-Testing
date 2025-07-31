package com.example;

import java.sql.*;
import java.io.*;
import java.util.*;


public class Bank {

    private final String url; // String url to hold the url for our SQLite DB

    public Bank(){ // standard constructor, intiiaties a connection to our bank.db and creates tables if not made already
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

    public Bank(String customUrl){ // primarily used for testing and ensuring we can catch SQLExceptions, takes in a random string.
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
    public boolean userlogin(String username, String password) { // user-login function that takes in a username and a password returns t/f based on result
        try (Connection conn = DriverManager.getConnection(url)){ // try to make connection db
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bankusers WHERE username = ? AND password = ?;"); // preparing a sql statement
            pstmt.setString(1, username); // inserting username into SQL statement
            pstmt.setString(2, password); // inserting password into SQL statement
            ResultSet rs =  pstmt.executeQuery(); // executing query, storing result in a ResultSet

            if (!rs.next()) { // if query resulted in nothing
                return false; // return false
                } else { 
                    return true;
                }
            } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Login Failed. " + e.getMessage());
            return false;
        }
    }

    public boolean addAccount(String username, String accountName){ // function to add an Account to a user, either savings or chequing
        try(Connection conn = DriverManager.getConnection(url)){
           PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bankusers WHERE username = ?;"); // geting user to make bankUser object
           pstmt.setString(1,username); 
            ResultSet rs = pstmt.executeQuery();
            BankUser owner = new BankUser(rs.getInt("id"), rs.getString("username"), rs.getString("password")); // making bankUser object
            BankAccount exists = getAccount(username, accountName); // retireving an acocunt of the same name for the user to check if account already eixsts
            
        if(exists == null && (accountName.equals("chequing") || accountName.equals("savings"))){ // if account doesn't already exit and the option is either chequing or savings, creates the account
            BankAccount accountBeingMade = new BankAccount(accountName, owner, 0.00, 0); // makes bankaccount object
            PreparedStatement insrt = conn.prepareStatement("INSERT INTO bankaccounts (accountType, accountOwner,accountBalance) VALUES(?,?,?);"); // SQL statement to insert into bankaccounts table
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

    public BankAccount getAccount(String username, String accountType){ // getAccount function, that retreives an account from a username and accountType argument, retruns a BankAccount object or null
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

public boolean depositToAcc(String username, String accountName, double amount){ // depositToAcc funfction, takes username, accountName, & amount arguments, returns T/F depending on result of function
        try (Connection conn = DriverManager.getConnection(url)){
            BankAccount account = getAccount(username, accountName);

            if (account == null){ // if account doesn't exist fail
            System.out.println("Failed to find account, please ensure this account exists.");
            return false;
            }
            if (amount <= 0 || amount > 10000 ) { // if invalid deposit amount, can only deposit $1 to $10,000
            System.out.println("Invalid deposit amount.");
            return false;
            }

            double newBalance = account.getAccountBalance() + amount; // newBalance varaible 
            PreparedStatement pstmt = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE accountOwner = ? AND accountType = ?;");

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, account.getAccountOwner().getUsername());
            pstmt.setString(3, account.getAccountType());
            pstmt.executeUpdate(); // inserting into DB the new balance of the account
            account.setAccountBalance(newBalance); // updating account object.
            System.out.println("Deposit successful. Balance: " + account.getAccountBalance());
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Deposit Failed. " + e.getMessage());
            return false;
        }
    }

    public boolean withdrawFromAcc(String username, String accountType, double withdrawAmount){ // withdrawFromAcc function, T/F based on success of operation
        try(Connection conn = DriverManager.getConnection(url)){
            BankAccount account = getAccount(username, accountType); // retireiving account
            if(account == null){  // if account is null/doesn't exist fail
                return false;
            }
            double currentBalance = account.getAccountBalance(); // get current balance of account
            if(withdrawAmount > currentBalance){
                System.out.println("Can't withdraw more than the account Balance");
                return false;
            }
            if (withdrawAmount <= 0) {
                System.out.println("Please withdraw a value greather than 0");
                return false;            
            }
            if(withdrawAmount > 5000){ // max withdraw limit of 5000
                System.out.println("You can only withdraw up to 5000");
                return false;
            }
             else {
                double newBalance = currentBalance - withdrawAmount; // update newBalance variable
                account.setAccountBalance(newBalance); // set account objet with new balance value
                PreparedStatement pstmt = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE id = ?;");
                pstmt.setDouble(1, newBalance);
                pstmt.setInt(2,account.getAccountId());
                int rowsUpdated = pstmt.executeUpdate(); // store result into int of how many rowsUpdated
                return rowsUpdated > 0; // True if update went through
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;

    }
    }

    public boolean deleteAccount(String username, String accountType){ // deleteAccount function with deletes an existing account of a user
        try(Connection conn = DriverManager.getConnection(url)){
            BankAccount accountExists = getAccount(username, accountType); // getting acocunt
            if(accountExists != null){ // as long as the account exists
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bankaccounts where accountOwner = ? AND accountType = ?");
                pstmt.setString(1,username);
                pstmt.setString(2, accountType);
                pstmt.executeUpdate(); // delete the account from the DB
                System.out.println(accountType + " account deleted succesfully");
                return true;
            }
            else{ // account doesn't exist therefore fail
                System.out.println("Failed to delete accounnt, please ensure the account exists and you type the correct account type");
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
    }
}

public void checkAccountBalances(String username, String accountType) { // checkAccountBalance function that retreivies balance of amount 
        try (Connection conn = DriverManager.getConnection(url)) {
            BankAccount account = getAccount(username, accountType);
            if (account != null) { // as long as account exists
                System.out.println("Account Type: " + account.getAccountType());
                System.out.println("Account Balance: " + account.getAccountBalance());
            } else {
                System.out.println("No account found for the specified type."); // account doesn't exit
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error checking account balances: " + e.getMessage());
        }
    }

    public boolean transferFunds(String username, String fromAccountType, String toAccountType, double amount){ // transferFunds function that allows a user to transfer funds between accounts
        try (Connection conn = DriverManager.getConnection(url)) {
            BankAccount fromAcc = getAccount(username, fromAccountType); // retrieving first account
            BankAccount toAcc = getAccount(username, toAccountType); // retrieving second account

            if(fromAcc == null || toAcc == null){ // neeither account can't not exist
                System.out.println("One of the accounts doesn't exist");
                return false;
            }
            if(amount <= 0){ // transfer amount must be at least $1
                System.out.println("Transfer amount must be greather than 0");
                return false;
            }
            if(amount > 7500){ // transfer amont can't be larger than $7500
                System.out.println("Can only transfer up to 7500");
                return false;
            }
            if(fromAcc.getAccountType().equals(toAcc.getAccountType())){ // both accounts can't be eachother, 
                System.out.println("Can't transfer from the same account to the same account");
                return false;
            }
            double fromBalance = fromAcc.getAccountBalance(); 
            if(fromBalance < amount){ // can't transfer more than the account has
                return false;
            }

            double newFromBalance = fromBalance - amount; // variables that update balances
            double newToBalance = toAcc.getAccountBalance() + amount;

            PreparedStatement updateFrom = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE id = ?;");
            updateFrom.setDouble(1, newFromBalance);
            updateFrom.setInt(2, fromAcc.getAccountId());
            int rows1 = updateFrom.executeUpdate(); // update From account

            PreparedStatement updateTo = conn.prepareStatement("UPDATE bankaccounts SET accountBalance = ? WHERE id = ?;");
            updateTo.setDouble(1, newToBalance);
            updateTo.setInt(2, toAcc.getAccountId());
            int rows2 = updateTo.executeUpdate(); // update to account
            
            fromAcc.setAccountBalance(newFromBalance); // update object balances
            toAcc.setAccountBalance(newToBalance);
            return rows1 > 0 && rows2 > 0; // if both updated successfuly return true
            

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}