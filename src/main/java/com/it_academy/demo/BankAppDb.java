package com.it_academy.demo;

import com.it_academy.model.Account;
import com.it_academy.model.Transaction;
import com.it_academy.service.BankAccountService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static com.it_academy.query_executor.UserQueryExecutor.*;

public class BankAppDb {
    private static final String JDBC_DRIVER_PATH = "org.sqlite.JDBC";
    private static final String DATABASE_URL = "jdbc:sqlite:c:\\Sqlite\\transaction.db";
    private static final int MAX_TRANSACTION = 100000000;

    public static void main(String[] args) throws SQLException {
        if (isDriverExists()) {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            String action;
            do {
                printMenu();
                System.out.println("\n The operation is:");
                action = new Scanner(System.in).nextLine();
                switch (action) {
                    case "1":
                        addUser(connection, BankAccountService.inputUser());
                        System.out.println("Operation is successful\n" + "Please, choose the operation\n");
                        break;
                    case "2":
                        Account account = BankAccountService.inputAccount();
                        if (checkCurrency(connection, account)) {
                            System.out.println("You already have account in this currency");
                        } else if (checkUserIdIsExist(connection, account)) {
                            addAccount(connection, account);
                            System.out.println("Operation is successful\n" + "Please, choose the operation\n");
                        } else {
                            System.out.println("User does not exist!");
                        }
                        break;
                    case "3":
                        Transaction transaction = BankAccountService.inputTransaction();
                        System.out.println("amount fot refill: ");
                        int amountForRefill = new Scanner(System.in).nextInt();
                        if (amountForRefill >= MAX_TRANSACTION) {
                            System.out.println("Error transaction!");
                            break;
                        } else if (checkAccountIdIsExist(connection, transaction)) {
                            createTransaction(connection, transaction, amountForRefill);
                            refillAccount(connection, transaction, amountForRefill);
                            System.out.println("Operation is successful\n" + "Please, choose the operation\n");
                        } else {
                            System.out.println("Account does not exist!");
                        }
                        break;
                    case "4":
                        Transaction transactionForDebit = BankAccountService.inputTransaction();
                        System.out.println("amount fot debit: ");
                        int amountForDebit = new Scanner(System.in).nextInt();
                        if (amountForDebit >= MAX_TRANSACTION) {
                            System.out.println("Error transaction!");
                            break;
                        } else if (checkAccountIdIsExist(connection, transactionForDebit)) {
                            createTransactionForDebit(connection, transactionForDebit, amountForDebit);
                            debitAccount(connection, transactionForDebit, amountForDebit);
                            System.out.println("Operation is successful\n" + "Please, choose the operation\n");
                        } else {
                            System.out.println("Account does not exist!");
                        }
                        break;
                    case "5":
                        System.out.println("Thanks for using the program!");
                        break;
                }
            } while (!"5".equals(action));
            connection.close();
        }


    }

    private static void printMenu() {
        System.out.println("Available Operations: \n" +
                "1 - Create new user\n" +
                "2 - Create new account\n" +
                "3 - Account refill\n" +
                "4 - Debit from the account\n"+
                "5 - Exit");
    }

    public static boolean isDriverExists() {
        try {
            Class.forName(JDBC_DRIVER_PATH);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC Driver was not found");
            return false;
        }
    }
}