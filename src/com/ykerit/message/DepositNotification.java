package com.ykerit.message;

import org.greatfree.message.ServerMessage;

// create by yker 04/09/2019

public class DepositNotification extends ServerMessage {

    private String username;
    private int amount;

    public DepositNotification(String username, int amount) {
        super(MessageType.DEPOSIT_NITIFICATION);
        this.username = username;
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public int getAmount() {
        return amount;
    }
}
