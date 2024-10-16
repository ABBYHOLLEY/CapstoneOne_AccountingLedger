package com.ps;

import java.io.BufferedReader; // To read data
import java.io.FileReader; // Also to read data
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException; // Used if user enters incorrect data
import java.util.Scanner;

public class Main {
    // Scanners to get data from user
    static Scanner commandScanner = new Scanner(System.in);
    static Scanner inputScanner = new Scanner(System.in);
    static ArrayList<Transaction> allTransactionsInLedger = new ArrayList<>();

    public static void main(String[] args) {
        loadAll();
        int mainMenuCommand;
        // Menu for user to choose three options
        do {
            System.out.println("Main Menu");
            System.out.println("1)Add Deposit");
            System.out.println("2)Make Payment");
            System.out.println("3)Ledger");
            System.out.println("0)Exit");
            System.out.println("Command: ");

            try { // Try block is for user to enter valid #, if not error message prints asking for valid input
                mainMenuCommand =
                        commandScanner.nextInt();
            } catch(InputMismatchException ime) {// Catches any errors+ prints message
                System.out.println("Invalid. Please enter a number.");
                ime.printStackTrace();
                commandScanner.nextInt();
                mainMenuCommand = 0;
            }
            switch (mainMenuCommand){
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    ledger();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Command not found! Please try again.");
            }
        } while (mainMenuCommand !=0); // Loop until user wants to stop

    }


    public static void reports(){
        int reportMenuCommand;
        do{
            System.out.println("Welcome to your reports!");
            System.out.println("What would you like to do?");
            System.out.println("1)Display month to date");
            System.out.println("2)Display previous month");
            System.out.println("3)Display year to date");
            System.out.println("4)Display previous year");
            System.out.println("5)Search by vendor");
            System.out.println("0)Back to home page");
            System.out.println("Command");

            try {
                reportMenuCommand = commandScanner.nextInt();
            }catch (InputMismatchException ime){
                reportMenuCommand = 0;
            }
            switch (reportMenuCommand){
                case 1:
                    displayMonthToDate();
                    break;
                case 2:
                    displayPreviousMonth();
                    break;
                case 3:
                    displayYearToDate();
                    break;
                case 4:
                    displayPreviousYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 0:
                    System.out.println("Returning to home page");
                    break;
                default:
                    System.out.println("Invalid command...Try again!");
            }
        } while (reportMenuCommand != 0);

    }
    public static void displayMonthToDate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        for (Transaction transaction: allTransactionsInLedger){
        if (transaction.getDate().isAfter(firstDayOfMonth)&&transaction.getDate().isBefore(currentDate))
            System.out.println(transaction);
        }
    }
    public static void displayPreviousMonth(){
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);
        for (Transaction transaction: allTransactionsInLedger){
            if (transaction.getDate().isAfter(previousMonthDate)&&transaction.getDate().isBefore(currentDate))
                System.out.println(transaction);
        }
    }
    public static void displayYearToDate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfYear = currentDate.withDayOfYear(1);
        for (Transaction transaction: allTransactionsInLedger){
            if (transaction.getDate().isAfter(firstDayOfYear)&&transaction.getDate().isBefore(currentDate))
                System.out.println(transaction);
        }
    }
    public static void displayPreviousYear(){
        LocalDate currentDate = LocalDate.now();
        LocalDate previousYearDate = currentDate.minusYears(1);
        for (Transaction transaction: allTransactionsInLedger){
            if (transaction.getDate().isAfter(previousYearDate)&&transaction.getDate().isBefore(currentDate))
                System.out.println(transaction);
        }
    }
    public static void searchByVendor(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter vendor name: ");
        String searchVendor = scanner.nextLine();
        for (Transaction transaction: allTransactionsInLedger){
            if (transaction.getVendor().equalsIgnoreCase(searchVendor)){
                System.out.println(transaction);
            }
        } scanner.close();
    }



    // Need first line of file to be skipped
    // Need files to be read one by one
    // Need file items to be split by a line so the data can be read easily

    public static void ledger(){
        int ledgerMenuCommand;
        do{
            System.out.println("Ledger screen");
            System.out.println("1)Display all entries");
            System.out.println("2)Display deposits going into account");
            System.out.println("3)Display payments going out of account");
            System.out.println("4)Display reports");
            System.out.println("0)Home");
            System.out.println("Command: ");

            try {
                ledgerMenuCommand = commandScanner.nextInt();
            } catch (InputMismatchException ime){
                ledgerMenuCommand = 0;
            }
            switch (ledgerMenuCommand){
                case 1:
                    displayAll();
                    break;
                case 2:
                    displayDeposit();
                    break;
                case 3:
                    displayPayments();
                    break;
                case 4:
                    reports();
                    break;
                case 0:
                    System.out.println("Returning to main menu");
                    break;
                default:
                    System.out.println("Invalid command...");
            }

        } while (ledgerMenuCommand != 0);

    }
    public static void addDeposit(){

        System.out.println("Please enter the details of the transaction");

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.println("Enter the transaction description: ");
        String description = inputScanner.nextLine();

        System.out.println("Enter the vendor: ");
        String vendor = inputScanner.nextLine();

        System.out.println("Enter the amount: ");
        String amountInput = inputScanner.nextLine();
        Float amount = Float.parseFloat(amountInput);

    }
    public static void makePayment(){
        System.out.println("Please enter the payment details");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

    }
    public static void loadAll(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv")); // Opens file.csv to read from it

            String firstLine = bufferedReader.readLine();// First line skipped
            String input;

            while ((input = bufferedReader.readLine()) != null){
                String[] transactionarr = input.split("\\|");

                LocalDate date = LocalDate.parse(transactionarr[0]);
                LocalTime time = LocalTime.parse(transactionarr[1]);
                String description = transactionarr[2];
                String vendor = transactionarr[3];
                Float amount = Float.parseFloat(transactionarr[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                allTransactionsInLedger.add(transaction);

                allTransactionsInLedger.add(new Transaction(date, time, description, vendor, amount));
            }
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void displayAll(){
      for (Transaction transaction: allTransactionsInLedger){
         System.out.println(transaction);
        }
    }
    public static void displayDeposit(){
        System.out.println("Testing displayDeposit");

    }
    public static void displayPayments(){
        System.out.println("Testing displayPayments");
    }
}
