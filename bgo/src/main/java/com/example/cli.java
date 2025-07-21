package com.example;

import java.util.Scanner;


public class cli {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        boolean auth = false;


        while (!auth){
            System.out.println("Hello and welcome to the BGO Financial Bank System, please select one of the options to begin!");
            System.out.println("Sign up: 1");
            System.out.println("Log in: 2");
            System.out.println("Exit: 3");
            System.out.println("Enter choice: ");

            int beginDecision = scanner.nextInt();
            scanner.nextLine();

            if(beginDecision == 1) {
                while(true){
                    System.out.println("Please choose a username or type 'MENU' to return to the option menu: ");
                    String usersignup = scanner.nextLine();
                    if(usersignup.equalsIgnoreCase("MENU")) break;

                    System.out.println("Please choose a password (must be 8 characters long)");
                    String passwordsignup = scanner.nextLine();

                    boolean signupresult = bank.usersignup(usersignup, passwordsignup);

                    if(signupresult){
                    System.out.println("Sign up Successfull");
                    auth = true;
                    break;
                } else{
                    System.out.println("Sign up unsuccessfull, please attempt again");
                }
                }
            }

            else if (beginDecision == 2) {
                while (true) {
                System.out.println("Please enter your username or type 'MENU' to return to the options menu: ");
                String userlogin = scanner.nextLine();
                if(userlogin.equalsIgnoreCase("MENU")) break;

                System.out.println("Please enter your password");
                String passwordlogin = scanner.nextLine();
                
                boolean loginresult = bank.userlogin(userlogin, passwordlogin); 
                
                if(loginresult){
                    System.out.println("Login Succesfull");
                    auth = true;
                    break;
                } else {
                    System.out.println("Login Unsuccesfull, please try again");
                }

                }
            }

            else if (beginDecision == 3) {
                System.out.println("Have a good day and see you next time!");
                scanner.close();
                return;
            }

            else {
                System.out.println("Please enter a valid option of 1,2, or 3");
            }
            // Main terminal

        }
    }
}