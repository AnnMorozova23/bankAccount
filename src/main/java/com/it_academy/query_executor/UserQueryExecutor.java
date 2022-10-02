package com.it_academy.query_executor;

import com.it_academy.model.Account;
import com.it_academy.model.Transaction;
import com.it_academy.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.format;

public class UserQueryExecutor {
    private static final int MAX_LIMIT = 2000000000;
    private static final int MIN_LIMIT = 0;


    public static void addUser(Connection connection, User user) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(format("INSERT INTO users (name, address) VALUES('%s', '%s')",
                user.getName(), user.getAddress()));
        statement.close();
    }

    public static void addAccount(Connection connection, Account account) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(format("INSERT INTO accounts (userId, balance, currency) VALUES('%d', '%d', '%s' )",
                account.getUserId(), account.getBalance(), account.getCurrency()));
        statement.close();
    }

    public static void createTransaction(Connection connection,
                                         Transaction transaction, int amountForRefill) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(format("INSERT INTO transactions (accountId, amount) VALUES('%d', '%d')",
                transaction.getAccountId(), amountForRefill));
        statement.close();
    }

    public static void createTransactionForDebit(Connection connection,
                                                 Transaction transaction, int amountForDebit) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(format("INSERT INTO transactions (accountId, amount) VALUES('%d', '-%d')",
                transaction.getAccountId(), amountForDebit));
        statement.close();
    }

    public static void refillAccount(Connection connection,
                                     Transaction transaction, int amountForRefill) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT balance FROM accounts " +
                "WHERE accountId = " + transaction.getAccountId());
        int balance = resultSet.getInt("balance");
        int max = balance + amountForRefill;
        if (max >= MAX_LIMIT) {
            System.out.println("limit is exceeded  2 000 000 000");
            statement.executeUpdate(format("UPDATE accounts SET balance = '%d' WHERE accountId = '%d'",
                    balance, transaction.getAccountId()));
        } else {
            statement.executeUpdate(format("UPDATE accounts SET balance = '%d' WHERE accountId = '%d'",
                    balance + amountForRefill, transaction.getAccountId()));
        }
        statement.close();
        resultSet.close();
    }

    public static void debitAccount(Connection connection,
                                    Transaction transaction, int amountForDebit) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT balance FROM accounts " +
                "WHERE accountId = " + transaction.getAccountId());
        int balance = resultSet.getInt("balance");
        int min = balance - amountForDebit;
        if (min < MIN_LIMIT) {
            System.out.println("Balance less than 0, operation is not possible");
            statement.executeUpdate(format("UPDATE accounts SET balance = '%d' WHERE accountId = '%d'",
                    balance, transaction.getAccountId()));
        } else {
            statement.executeUpdate(format("UPDATE accounts SET balance = '%d' WHERE accountId = '%d'",
                    balance - amountForDebit, transaction.getAccountId()));
        }
        statement.close();
        resultSet.close();
    }


    public static boolean checkCurrency(Connection connection, Account account) throws SQLException {
        int userID = account.getUserId();
        String currency = account.getCurrency();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT currency FROM accounts WHERE userId = " + userID +
                " AND currency = '" + currency + "'");
        if (resultSet.next()) {
            statement.close();
            resultSet.close();
            return true;
        } else {
            statement.close();
            resultSet.close();
            return false;
        }

    }

    public static boolean checkUserIdIsExist(Connection connection, Account account) throws SQLException {
        int userID = account.getUserId();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userId FROM users WHERE userId = " + userID);
        if (resultSet.next()) {
            statement.close();
            resultSet.close();
            return true;
        } else {
            statement.close();
            resultSet.close();
            return false;
        }

    }

    public static boolean checkAccountIdIsExist(Connection connection, Transaction transaction) throws SQLException {
        int accountID = transaction.getAccountId();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT accountId FROM accounts " +
                "WHERE accountId = " + accountID);
        if (resultSet.next()) {
            statement.close();
            resultSet.close();
            return true;
        } else {
            statement.close();
            resultSet.close();
            return false;
        }

    }
}
