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

            }
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public static void addDeposit(){

        System.out.println("Please enter the details of the transaction");

        // Need current date for deposit
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Need current time for deposit
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.println("Enter the transaction description: ");
        String description = inputScanner.nextLine();

        System.out.println("Enter the vendor: ");
        String vendor = inputScanner.nextLine();

        System.out.println("Enter deposit amount (please use a positive value to make a deposit): ");
        String amountInput = inputScanner.nextLine();
        Float amount = Float.parseFloat(amountInput); // Float because it could be use a decimal

        // Create a new transaction
        Transaction transaction = new Transaction(LocalDate.parse(date), LocalTime.parse(time), description, vendor, amount);
        allTransactionsInLedger.add(transaction); // Add the transaction to ledger

        System.out.println("Deposit added!"); // Confirm that user added deposit
        System.out.println(transaction); // Print out transaction for user to see

    }
    public static void makePayment(){
        System.out.println("Please enter the payment details: ");

        // Need the current date and time for payment
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Get payment description
        System.out.println("Enter the payment description: ");
        String description = inputScanner.nextLine();

        // Get vendor name from user
        System.out.println("Enter vendor name: ");
        String vendor = inputScanner.nextLine();

        // Get payment amount from user
        System.out.println("Enter payment amount (Please use a negative value to make a payment): ");
        float amount = inputScanner.nextFloat();
        inputScanner.nextLine();

        // Check if amount inputed is positive
        if (amount > 0){
            amount = -amount; // Payments will decrease the balance, that is why it is negative
        }
        // Create transaction object for payment
        Transaction transaction = new Transaction(LocalDate.parse(date), LocalTime.parse(time), description, vendor, amount);

        // Add transaction to ledger
        allTransactionsInLedger.add(transaction);// come back to fix this


        System.out.println("Payment added, thank you!");
        // come back to fix this and add a few things
        System.out.println(transaction);
    }
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

    public static void displayAll(){
        for (Transaction transaction: allTransactionsInLedger){
            System.out.println(transaction);
        }
    }
    public static void displayDeposit(){
        System.out.println("Displaying all deposits");

        // Check to see if there are any deposits in ledger
        if (allTransactionsInLedger.isEmpty()){
            System.out.println("Not transactions match your search.");
        } else {
            // Loop through the transactions to display all deposits
            for (Transaction transaction : allTransactionsInLedger){
                if (transaction.getAmount()>0){
                    System.out.println(transaction); // Print deposit transaction
                }
            }
        }
    }
    public static void displayPayments(){
        System.out.println("Displaying all payments");

        // Make sure that the transaction is in the ledger
        if (allTransactionsInLedger.isEmpty()){
            System.out.println("No transactions match your search.");
        }else {
            // Loop through transactions and only show payments
            for (Transaction transaction : allTransactionsInLedger){
                if (transaction.getAmount()<0){
                    System.out.println(transaction); // Print payment transaction
                }
            }
        }
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
                case 6:
                    customSearch();
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
        LocalDate currentDate = LocalDate.now(); // Current date
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1); // Getting the day of month you want (1st of month)
        for (Transaction transaction: allTransactionsInLedger){ // Searching through all transactions in ledger

         //   if transaction is after the first day of the month and before current date, print transaction
        if (transaction.getDate().isAfter(firstDayOfMonth)&&transaction.getDate().isBefore(currentDate))
            System.out.println(transaction);
        }
    }
    public static void displayPreviousMonth(){
        LocalDate currentDate = LocalDate.now(); // Needs current date to figure out previous month
        LocalDate previousMonthDate = currentDate.minusMonths(1); // Subtracting one month from current date
        for (Transaction transaction: allTransactionsInLedger){ // Searching through all transactions in Ledger

            // if transaction is one month before current date, print out transaction
            if (transaction.getDate().isAfter(previousMonthDate)&&transaction.getDate().isBefore(currentDate))
                System.out.println(transaction);
        }
    }
    // Years are exactly like dates, basically switch out months to years
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

            // If vendor is the vendor that the user is searching for (while ignoring case sensitivity) print out transaction
            if (transaction.getVendor().equalsIgnoreCase(searchVendor)){
                System.out.println(transaction);
            }
        } scanner.close();
    }

    public static void customSearch(){ // Need to work on custom search and research different ways to execute
        int customSearchMenuCommand;
        do {
        System.out.println("Custom Search");
        System.out.println("What do you want to search by?");
        System.out.println("1)Date range");
        System.out.println("2)Description");
        System.out.println("3)Amount");
        System.out.println("0)Back to reports page..");
        System.out.println("Command");
        try {
            customSearchMenuCommand = commandScanner.nextInt();
        }catch (InputMismatchException ime){
            customSearchMenuCommand = 0;
        }
        switch (customSearchMenuCommand){
            case 1:
                dateRange();
                break;
            case 2:
                description();
                break;
            case 3:
                amount();
                break;
            case 0:
                System.out.println("Returning to reports page..");
                break;
            default:
                System.out.println("Invalid command...Try again!");
            }

        }while (customSearchMenuCommand != 0);
    }
    public static void dateRange(){
        System.out.println("Enter the start date of the transaction you are searching for(yyyy-mm-dd): ");
        String startDateInput = inputScanner.nextLine();
        System.out.println("Enter the end date of the transaction you are searching for(yyyy-mm-dd): ");
        String endDateInput = inputScanner.nextLine();

        LocalDate startDate = LocalDate.parse(startDateInput);
        LocalDate endDate = LocalDate.parse(endDateInput);

        for (Transaction transaction : allTransactionsInLedger){
            if (!transaction.getDate().isBefore(startDate)&&!transaction.getDate().isAfter(endDate)){
                System.out.println(transaction);
            }
        }
    }
    public static void description(){
        System.out.println("Enter the description of the the transaction you are searching for.");
        String description = inputScanner.nextLine();

        for (Transaction transaction : allTransactionsInLedger){
            if (transaction.getDescription().equalsIgnoreCase(description)){ // Need to work on this
                System.out.println(transaction);
            }
        }
    }
    public static void amount(){
        System.out.println("Enter the amount of the transaction you are searching for.");
    }

    // Need first line of file to be skipped
    // Need files to be read one by one
    // Need file items to be split by a line so the data can be read easily

}
