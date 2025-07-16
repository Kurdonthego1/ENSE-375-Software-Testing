package com.example;

import java.util.Scanner;


public class cli {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        System.out.println("Hello and welcome to the BGO Financial Bank System, please select one of the options to begin: 1 to sign-up, 2 to login");
        int beginDecision = scanner.nextInt(); //this is a method that reads an integer from the terminal


        while (true){
            // Main terminal


            // Signup
            while(true){
            if(beginDecision == 1){
                System.out.println("Please choose a username");
                String usersignup = scanner.nextLine(); //this is a method that reads a text from user inn terminal 
                System.out.println("Please choose a password");
                String passwordsignup = scanner.nextLine();
                boolean signupresult = bank.usersignup(usersignup, passwordsignup);
                if(signupresult){
                    System.out.println("Sign up Successfull");
                    break;
                }
                else{
                    System.out.println("Sign up unsuccessfull, please attempt again");
                }
            }
            }
            // Login
            if(beginDecision == 2){
                while(true){
                    System.out.println("Please enter your username:");
                    String userlogin = scanner.nextLine();
                    System.out.println("Please enter your password");
                    String passwordlogin = scanner.nextLine();
                    boolean loginresult = bank.userlogin(userlogin, passwordlogin);
                    if(loginresult){
                        System.out.println("Login Succesfull");
                        System.out.println("Welcome" + " " + userlogin + " " + "Please select one of the options below:");
                        System.out.println("1, 2, 3,");
                    } else {
                        System.out.println("Login Unsuccesfull, please try again");
                        break;
                    }
                }   
            }
            
            else{
                System.out.println("Please enter a valid value");
                }


            // Placeholder for logout
            System.out.println("3. Exit");
        
    }
    
}
}