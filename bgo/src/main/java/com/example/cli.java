package com.example;

import java.util.Scanner;


public class cli {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        String username = "";
        Bank bank = new Bank();
        boolean auth = false;
        boolean exit = false;


        while (!auth){ // Begining Menu
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
                    username = usersignup;
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
                    username = userlogin;
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
        }
        // Main Terminal

        while(!exit){
            System.out.println("Please select one of the following options");
            System.out.println("Make Account: 1");
            System.out.println("Deposit into Account: 2");
            System.out.println("Withdraw from Account: 3");
            System.out.println("View Account Balance: 4");
            System.out.println("Exit: 5");

            int menuDecision = scanner.nextInt();
            scanner.nextLine();

            if(menuDecision == 1){
                System.out.println("Please choose what type of account you would like to open, chequing or savings");
                String accountType = scanner.nextLine();
                boolean acresult = bank.makeAccount(username, accountType);
                if(acresult){
                    System.out.println("Successfully created " + accountType + " account");
                } else{
                    System.out.println("Account either already exists or invalid account type chosen");
                }
            }

            else if(menuDecision == 2){
                // Deposit into Account
            }

            else if(menuDecision == 3){
                // Withdraw from Account
            }

            else if (menuDecision == 4){
                // View Account Balance
            }

            else if(menuDecision == 5){
                exit = true;
            }

            else{
                System.out.println("Please enter a valid option of 1,2,3,4, or 5");
            }



        }
        System.out.println("Have a good day and see you next time!");
        scanner.close();
        return;
    }
}