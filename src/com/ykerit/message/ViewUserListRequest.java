package com.ykerit.message;

import org.greatfree.message.ServerMessage;

public class ViewUserListRequest extends ServerMessage {

    private String query;

    public ViewUserListRequest(String query) {
        super(MessageType.VIEW_USER_LIST_REQUEST);
        this.query = query;
    }
    public String getQuery() {
        return this.query;
    }
}
