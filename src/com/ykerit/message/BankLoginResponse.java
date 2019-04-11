package com.ykerit.message;

import org.greatfree.message.ServerMessage;

public class BankLoginResponse extends ServerMessage {

    private Boolean status;

    public BankLoginResponse(Boolean status) {
        super(MessageType.BANKLOGIN_RESPONSE);
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }
}
