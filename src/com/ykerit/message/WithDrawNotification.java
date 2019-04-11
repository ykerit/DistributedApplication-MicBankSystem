package com.ykerit.message;

import org.greatfree.message.ServerMessage;

public class WithDrawNotification extends ServerMessage {

    private String username;
    private int amount;

    public WithDrawNotification(String username, int amount) {
        super(MessageType.WITHDRAW_NITIFICATION);
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
