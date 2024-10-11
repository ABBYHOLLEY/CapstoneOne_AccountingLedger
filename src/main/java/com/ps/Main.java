package com.ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Main {

    static Scanner commandScanner = new Scanner(System.in);
    static Scanner inputScanner = new Scanner(System.in);
    static ArrayList<Transaction> allTransactionsInLedger = new ArrayList<>();

    public static void main(String[] args) {
        int mainMenuCommand;

        do {
            System.out.println("Please enter an option!");
            System.out.println("1)Look up transaction");
            System.out.println("2)Display all transactions");
            System.out.println("0)Exit");
            System.out.println("Command: ");
            try {
                mainMenuCommand =
                        commandScanner.nextInt();
            } catch(InputMismatchException ime) {
                System.out.println("Invalid. Please enter a number.");
                commandScanner.next();
                mainMenuCommand = 0;
            }
            switch (mainMenuCommand){
                case 1:
                    addTransaction();
                    break;
                case 2:
                    displayAllTransactions();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Command not found! Please try again.");
            }
        } while (mainMenuCommand !=0); // Loop until user wants to stop

    }

    public static void displayAllTransactions(){
      try {
          BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));

          String firstLine = bufferedReader.readLine();
          String input;

          while ((input = bufferedReader.readLine()) != null){
              String[] transactionarr = input.split("\\|");

              LocalDateTime date = LocalDateTime.parse(date, DateTimeFormatter.);
              LocalDateTime time = LocalDateTime.parse(time, DateTimeFormatter.);
              String description = transactionarr[2];
              String vendor = transactionarr[3];
              Float amount = Float.parseFloat(input);


          }
      }
    }
}