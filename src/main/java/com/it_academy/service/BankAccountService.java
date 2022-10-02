package com.it_academy.service;

import com.it_academy.model.Account;
import com.it_academy.model.Transaction;
import com.it_academy.model.User;

import java.util.Scanner;


public class BankAccountService {


    public static User inputUser() {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter your name and lastname: ");
        user.setName(scanner.nextLine());
        System.out.println("Enter your address: ");
        user.setAddress(scanner.nextLine());
        return user;
    }

    public static Transaction inputTransaction() {
        Transaction transaction = new Transaction();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter accountId to complete the transaction: ");
        transaction.setAccountId(scanner.nextInt());

        return transaction;
    }

    public static Account inputAccount() {
        Account account = new Account();
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter your userID: ");
        account.setUserId(scanner.nextInt());
        printAvailableCurrency();
        String action = new Scanner(System.in).nextLine();
        switch (action) {
            case "1":
                account.setCurrency("BYN");
                break;
            case "2":
                account.setCurrency("USD");
                break;
            case "3":
                account.setCurrency("EUR");
                break;
            case "4":
                account.setCurrency("RUB");
                break;
        }
        account.setBalance(0);
        return account;
    }

    private static void printAvailableCurrency() {
        System.out.println("Available currency: \n" +
                "1 - BYN \n" +
                "2 - USD \n" +
                "3 - EUR \n" +
                "4 - RUB ");
    }
}
