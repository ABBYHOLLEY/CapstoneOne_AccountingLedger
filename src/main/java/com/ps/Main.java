package com.ps;

import java.io.BufferedReader; // To read data
import java.io.FileReader; // Also to read data
import java.sql.SQLOutput;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException; // Used if user enters incorrect data
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Main {
    // Scanners to get data from user
    static Scanner commandScanner = new Scanner(System.in);
    static Scanner inputScanner = new Scanner(System.in);
    static ArrayList<Transaction> allTransactionsInLedger = new ArrayList<>();

    public static void main(String[] args) {
        int mainMenuCommand;
        // Menu for user to choose three options
        do {
            System.out.println("Please enter an option!");
            System.out.println("1)Look up transaction");
            System.out.println("2)Display all transactions");
            System.out.println("0)Exit");
            System.out.println("Command: ");

            try { // Try block is for user to enter valid #, if not error message prints asking for valid input
                mainMenuCommand =
                        commandScanner.nextInt();
            } catch(InputMismatchException ime) {// Catches any errors+ prints message
                System.out.println("Invalid. Please enter a number.");
                commandScanner.next();
                mainMenuCommand = 0;
            }
            switch (mainMenuCommand){
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                case 3:
                    displayAllTransactions();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Command not found! Please try again.");
            }
        } while (mainMenuCommand !=0); // Loop until user wants to stop

    }

    // Need first line of file to be skipped
    // Need files to be read one by one
    // Need file items to be split by a line so the data can be read easily

    public static void displayAllTransactions(){
      try {
          BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv")); // Opens file.csv to read from it

          String firstLine = bufferedReader.readLine();// First line skipped
          String input;

          while ((input = bufferedReader.readLine()) != null){
              String[] transactionarr = input.split("\\|");

              LocalDateTime date = LocalDateTime.parse(transactionarr[0], DateTimeFormatter.ofPattern("yy-MM-dd"));
              LocalDateTime time = LocalDateTime.parse(transactionarr[1], DateTimeFormatter.ofPattern("HH:mm:ss"));
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
    public static void addTransaction(){
        System.out.println("Please enter the details of the transaction");

        System.out.println("Date: ");
        LocalDateTime date = inputScanner.nextLine();

        System.out.println("Time: ");

        System.out.println("Description: ");
        String description = inputScanner.nextLine();

        System.out.println("Vendor: ");
        String vendor = inputScanner.nextLine();

        System.out.println("Amount: ");
        String amount = inputScanner.nextLine();


    }
}