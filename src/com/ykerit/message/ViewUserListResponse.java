package com.ykerit.message;

import org.greatfree.message.ServerMessage;

public class ViewUserListResponse extends ServerMessage {

    private String response;
    public ViewUserListResponse(String response) {
        super(MessageType.CHECKACCOUNT_RESPONSE);
        this.response = response;
    }

    public String getResponse() {
        return this.response;
    }
}
