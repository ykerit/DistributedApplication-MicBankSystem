package com.ykerit.message;

import org.greatfree.message.ServerMessage;

public class CheckAccountResponse extends ServerMessage {

    private String response;
    public CheckAccountResponse(String response) {
        super(MessageType.VIEW_USER_LIST_RESPONSE);
        this.response = response;
    }

    public String getResponse() {
        return this.response;
    }
}
