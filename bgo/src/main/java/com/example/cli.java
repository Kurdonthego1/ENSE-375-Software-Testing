package com.example;

import java.util.Scanner;

public class cli {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // creating scanner object to take in user inpput
        Bank bank = new Bank(); // creatilg bank object
        String username = null; // initalizing empty username variable
        boolean authenticated = false; // intailizing and setting first menu loop variable to false

        
        while (!authenticated) {  // first loop, login/exit 
            System.out.println("BGO Financial Bank System");
            System.out.println("1) Log in");
            System.out.println("2) Exit");
            System.out.print("Enter choice: ");

            if(!scanner.hasNextInt()){ // checking that user inputed an int
                System.out.println("Invalid option - Please enter a number (1 or 2)");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt(); // grabbing int
            scanner.nextLine();

            switch (choice) {
                case 1: // login option
                    System.out.print("Username (or MENU to cancel): ");
                    String user = scanner.nextLine(); // grab username
                    if (user.equalsIgnoreCase("MENU")) break; // if menu then exit to authetntication loop menu

                    System.out.print("Password: ");
                    String password = scanner.nextLine(); // grab password

                    if (bank.userlogin(user, password)) { // if susscessfuly logged in set username and change authentication loop varaible to true to break loop
                        System.out.println("Login successful!");
                        username = user;
                        authenticated = true;
                    } else { // else fail and keep in log in loop until "menu" or successful exit
                        System.out.println("Login failed. Please try again!");
                    }
                    break;

                case 2: // exit option
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default: // neither option 
                    System.out.println("Please enter 1 or 2.");
            }
        }

        //Authenticated menu
        boolean exit = false; // options menu variable
        while (!exit) { // options menu loop
            System.out.println("--- Main Menu ---");
            System.out.println("1) Open a new account (chequing/savings)");
            System.out.println("2) Delete an existing account (chequing/savings)");
            System.out.println("3) Deposit");
            System.out.println("4) Withdraw");
            System.out.println("5) Check balances");
            System.out.println("6) Transfer between accounts");
            System.out.println("7) Exit");
            System.out.print("Enter choice: ");

            if(!scanner.hasNextInt()){ // checking that value is a proper int
                System.out.println("Invalid option - Please enter a number (1-7)");
                scanner.nextLine();
                continue;
            }

            int menu = scanner.nextInt(); // grabbing choice
            scanner.nextLine();

            switch (menu) {
                case 1: // addAcount option
                    System.out.print("Account type to open (chequing/savings): ");
                    String acctType = scanner.nextLine();
                    if (bank.addAccount(username, acctType)) { // sucessfuly made account
                        System.out.println("Created " + acctType + " account.");
                    } else { // failed
                        System.out.print("\n Account creation failed, either account already exists or invalid account type.");

                    }
                    break;

                case 2: // deleteAccount option
                    System.out.print("Account type delete (chequing/savings): "); // grabbing account type
                    String accType = scanner.nextLine();
                    if(bank.deleteAccount(username, accType)){ // if sucessful
                        System.out.println("Deleted " + accType + " account.");
                    } else { // if failed
                        System.out.print("\n");
                    }
                    break;

                case 3: // deposit into account option
                    System.out.print("Deposit to which account? (chequing/savings): ");
                    String depAcct = scanner.nextLine(); // grabbing account
                    System.out.print("Amount to deposit: "); // grabbing amount
                    if(!scanner.hasNextDouble()){ // checking that amount is a proper doouble valie
                        System.out.println("You can only deposit a number!");
                        break;
                    }
                    double depAmt = scanner.nextDouble();
                    scanner.nextLine();
                    if (bank.depositToAcc(username,depAcct, depAmt)) { // if successful
                        System.out.printf("New %s balance: %.2f%n",
                            depAcct, bank.getAccount(username, depAcct).getAccountBalance());
                    } else { // if failed
                        System.out.println("Deposit failed.");
                    }
                    break;

                case 4: // withdraw from account option
                    System.out.print("Withdraw from which account? (chequing/savings): ");
                    String wAcct = scanner.nextLine(); // grabbing account
                    System.out.print("Amount to withdraw: ");
                    if(!scanner.hasNextDouble()){ // checking that withdraw amount is a proper double
                        System.out.println("You can only withdraw a number!");
                        break;
                    }
                    double wAmt = scanner.nextDouble(); // grabbing amount
                    scanner.nextLine();
                    if (bank.withdrawFromAcc(username, wAcct, wAmt)) { // if successful
                        System.out.printf("New %s balance: %.2f%n",
                            wAcct, bank.getAccount(username, wAcct).getAccountBalance());
                    } else { // if failed
                        System.out.println("Withdrawal failed.");
                        System.out.print("\n"); 

                    }
                    break;

                case 5: // view account balance option
                BankAccount che = bank.getAccount(username,"chequing"); // grabbing chequing account
                BankAccount sav = bank.getAccount(username, "savings"); // grabbing savings account
                if(che != null){ // if chequing account eixsts
                    System.out.println("--------- Chequing Balance: ---------");
                    bank.checkAccountBalances(username, "chequing");
                    System.out.print("\n");
                } 
                if(sav != null){ // if savings account exists
                    System.out.println("--------- Savings Balance ---------");
                    bank.checkAccountBalances(username, "savings");
                    System.out.print("\n"); 
                }
                if(sav == null && che == null){ // if neither exist
                    System.out.println("No accounts made, please make accounts first to view their balance.");
                    System.out.print("\n"); 
                }
                    break;

                case 6: // Transfer account option
                    System.out.print("From (chequing/savings): "); // grabbing from account
                    String from = scanner.nextLine();
                    System.out.print("To   (chequing/savings): "); // gravving to account
                    String to = scanner.nextLine();
                    System.out.print("Amount to transfer: "); // grabbing amount to transfer
                    double tAmt = scanner.nextDouble();
                    if(!scanner.hasNextDouble()){ // check amount is a double
                        System.out.println("You can only transfer a number!");
                        break;
                    }
                    scanner.nextLine();
                    if (bank.transferFunds(username, from, to, tAmt)) { // if successful
                        System.out.println("Transfer successful!");
                        System.out.printf("  %s: %.2f%n", from, bank.getAccount(username, from).getAccountBalance());
                        System.out.printf("  %s: %.2f%n", to,   bank.getAccount(username, to).getAccountBalance());
                    } else { // failed
                        System.out.println("Transfer failed.");
                    }
                    break;

                case 7: // wants to exit, 
                    exit = true; // set loop variable
                    break;

                default:
                    System.out.println("Please enter 1–7.");
            }
        }

        System.out.println("Thanks for banking with BGO!!"); // exit message
        scanner.close();
    }
}
