package com.ykerit.server;

import com.ykerit.message.ShutdownBankServerNotification;
import org.greatfree.concurrency.interactive.NotificationThreadCreatable;

public class ShutdownBankServerThreadCreator implements NotificationThreadCreatable<ShutdownBankServerNotification, ShutdownBankServerThread> {
    @Override
    public ShutdownBankServerThread createNotificationThreadInstance(int size) {
        return new ShutdownBankServerThread(size);
    }
}
