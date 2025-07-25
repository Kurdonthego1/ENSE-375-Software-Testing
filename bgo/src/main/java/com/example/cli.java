package com.example;

import java.util.Scanner;

public class cli {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        String username = null;
        boolean authenticated = false;

        
        while (!authenticated) {
            System.out.println("BGO Financial Bank System");
            System.out.println("1) Log in");
            System.out.println("2) Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Username (or MENU to cancel): ");
                    String user = scanner.nextLine();
                    if (user.equalsIgnoreCase("MENU")) break;

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    if (bank.userlogin(user, password)) {
                        System.out.println("Login successful!");
                        username = user;
                        authenticated = true;
                    } else {
                        System.out.println("Login failed. Please try again!");
                    }
                    break;

                case 2:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Please enter 1 or 2.");
            }
        }

        //Authenticated menu
        boolean exit = false;
        while (!exit) {
            System.out.println("--- Main Menu ---");
            System.out.println("1) Open a new account (chequing/savings)");
            System.out.println("2) Delete an existing account (chequing/savings)");
            System.out.println("3) Deposit");
            System.out.println("4) Withdraw");
            System.out.println("5) Check balances");
            System.out.println("6) Transfer between accounts");
            System.out.println("7) Exit");
            System.out.print("Enter choice: ");

            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    System.out.print("Account type to open (chequing/savings): ");
                    String acctType = scanner.nextLine();
                    if (bank.addAccount(username, acctType)) {
                        System.out.println("Created " + acctType + " account.");
                    } else {
                        System.out.println("Failed to create account (already exists or invalid).");
                        System.out.print("\n");

                    }
                    break;

                case 2:
                    System.out.print("Account type delete (chequing/savings): ");
                    String accType = scanner.nextLine();
                    if(bank.deleteAccount(username, accType)){
                        System.out.println("Deleted " + accType + " account.");
                    } else {
                        System.out.print("\n");
                    }
                    break;

                case 3:
                    System.out.print("Deposit to which account? (chequing/savings): ");
                    String depAcct = scanner.nextLine();
                    System.out.print("Amount to deposit: ");
                    double depAmt = scanner.nextDouble();
                    scanner.nextLine();
                    BankAccount depAccount = bank.getAccount(username, depAcct);
                    if (depAccount != null && bank.depositToAcc(depAccount, depAmt)) {
                        System.out.printf("New %s balance: %.2f%n",
                            depAcct, bank.getAccount(username, depAcct).getAccountBalance());
                    } else {
                        System.out.println("Deposit failed.");
                    }
                    break;

                case 4:
                    System.out.print("Withdraw from which account? (chequing/savings): ");
                    String wAcct = scanner.nextLine();
                    System.out.print("Amount to withdraw: ");
                    double wAmt = scanner.nextDouble();
                    scanner.nextLine();
                    if (bank.withdrawFromAcc(username, wAcct, wAmt)) {
                        System.out.printf("New %s balance: %.2f%n",
                            wAcct, bank.getAccount(username, wAcct).getAccountBalance());
                    } else {
                        System.out.println("Withdrawal failed.");
                        System.out.print("\n"); 

                    }
                    break;

                case 5:
                    System.out.println("--------- Chequing Balance: ---------");
                    bank.checkAccountBalances(username, "chequing");
                    System.out.print("\n"); 
                    System.out.println("--------- Savings Balance ---------");
                    bank.checkAccountBalances(username, "savings");
                    System.out.print("\n"); 

                    break;

                case 6:
                    System.out.print("From (chequing/savings): ");
                    String from = scanner.nextLine();
                    System.out.print("To   (chequing/savings): ");
                    String to = scanner.nextLine();
                    System.out.print("Amount to transfer: ");
                    double tAmt = scanner.nextDouble();
                    scanner.nextLine();
                    if (bank.transferFunds(username, from, to, tAmt)) {
                        System.out.println("Transfer successful!");
                        System.out.printf("  %s: %.2f%n", from, bank.getAccount(username, from).getAccountBalance());
                        System.out.printf("  %s: %.2f%n", to,   bank.getAccount(username, to).getAccountBalance());
                    } else {
                        System.out.println("Transfer failed.");
                    }
                    break;

                case 7:
                    exit = true;
                    break;

                default:
                    System.out.println("Please enter 1–6.");
            }
        }

        System.out.println("Thanks for banking with BGO!!");
        scanner.close();
    }
}
