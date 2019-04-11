package com.ykerit.server;

import com.ykerit.message.WithDrawNotification;
import org.greatfree.concurrency.interactive.NotificationThreadCreatable;

public class WithDrawNotificationThreadCreator implements NotificationThreadCreatable<WithDrawNotification, WithDrawNotificationThread> {
    @Override
    public WithDrawNotificationThread createNotificationThreadInstance(int size) {
        return new WithDrawNotificationThread(size);
    }
}
