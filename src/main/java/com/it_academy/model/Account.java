package com.it_academy.model;

public class Account {
    private int userId;
    private int balance;
    private String currency;

    public int getUserId() {
        return userId;
    }

    public int getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (userId != account.userId) return false;
        if (balance != account.balance) return false;
        return currency != null ? currency.equals(account.currency) : account.currency == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + balance;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
