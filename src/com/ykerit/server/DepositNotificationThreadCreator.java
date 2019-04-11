package com.ykerit.server;


import com.ykerit.message.DepositNotification;
import org.greatfree.concurrency.interactive.NotificationThreadCreatable;

public class DepositNotificationThreadCreator implements NotificationThreadCreatable<DepositNotification, DepositNotificationThread> {
    @Override
    public DepositNotificationThread createNotificationThreadInstance(int size) {
        return new DepositNotificationThread(size);
    }
}
