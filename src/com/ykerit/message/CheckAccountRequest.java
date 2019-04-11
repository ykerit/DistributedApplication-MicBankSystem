package com.ykerit.message;

import org.greatfree.message.ServerMessage;

public class CheckAccountRequest extends ServerMessage {


    private String username;

    public CheckAccountRequest(String username) {
        super(MessageType.CHECKACCOUNT_REQUEST);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
