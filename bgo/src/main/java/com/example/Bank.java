package com.example;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Bank {

    private static final String url = "jdbc:sqlite:bank.db";

    public boolean usersignup(String username, String password){
        //Vibe coder
        Pattern p = Pattern.compile("^.{8,}$");
        Matcher m = p.matcher(password);

        boolean validpassword = m.matches();

        if(!validpassword){
            return false;
        }
        try (Connection conn = DriverManager.getConnection(url)){

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS bankusers (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL);");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bankusers (username, password) VALUES (?,?);");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Signup Failed. " + e.getMessage());
            return false;
        } //Placeholder before implementation and refactoring
    }
    
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

    
}
