package com.ykerit.message;

import org.greatfree.message.ServerMessage;

public class ShutdownBankServerNotification extends ServerMessage {
    public ShutdownBankServerNotification() {
        super(MessageType.SHUTDOWN_NITIFICATION);
    }
}
