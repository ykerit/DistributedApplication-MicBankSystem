package com.ykerit.message;

import org.greatfree.message.ServerMessage;

public class BankLoginRequest extends ServerMessage {

    private String username;

    public BankLoginRequest(String username) {
        super(MessageType.BANKLOGIN_REQUEST);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
