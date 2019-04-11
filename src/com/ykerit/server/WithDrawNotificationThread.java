package com.ykerit.server;

import com.ykerit.message.UserAccount;
import com.ykerit.message.UserRecord;
import com.ykerit.message.WithDrawNotification;
import org.greatfree.concurrency.interactive.NotificationQueue;
import org.greatfree.data.ServerConfig;

public class WithDrawNotificationThread extends NotificationQueue<WithDrawNotification> {

    public WithDrawNotificationThread(int taskSize) {
        super(taskSize);
    }

    @Override
    public void run() {
        WithDrawNotification notification;

        while (!this.isShutdown()) {
            while (!this.isEmpty()) {
                try {
                    notification = this.getNotification();
                    notification.getUsername();
                    UserAccount userAccount = AccountRedux.CS().getAccount(notification.getUsername());
                    userAccount.WithDraw(notification.getAmount());
                    UserRecord userRecord =  userAccount.getUserRecord();
                    userRecord.setType("WITHDRAW");
                    userRecord.setAmount(notification.getAmount());

                    System.out.println(userAccount.getUsername() + " "+
                            userAccount.getUserRecord().getType() + " " +
                            userAccount.getUserRecord().getAmount());


                    this.disposeMessage(notification);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                this.holdOn(ServerConfig.NOTIFICATION_THREAD_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
