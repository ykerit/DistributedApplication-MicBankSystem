package com.ykerit.message;

import java.io.Serializable;

public class UserAccount implements Serializable {

    private String username;
    private int accountBalance;
    private UserRecord userRecord;

    public UserAccount(String username, int accountBalance, UserRecord userRecord) {
        this.username = username;
        this.accountBalance = accountBalance;
        this.userRecord = userRecord;
    }

    public String getUsername() {
        return this.username;
    }

    public int getAccountBalance() {
        return this.accountBalance;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void WithDraw(int money) {
        if (this.accountBalance >= money) {
            this.accountBalance -= money;
        } else {
            System.out.println("Sorry, your credit is running low");
        }
    }
    public void Deposit(int money) {
        this.accountBalance += money;
    }
 }
