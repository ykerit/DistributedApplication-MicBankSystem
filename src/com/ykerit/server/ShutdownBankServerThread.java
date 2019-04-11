package com.ykerit.server;

import com.ykerit.message.ShutdownBankServerNotification;
import org.greatfree.concurrency.interactive.NotificationQueue;
import org.greatfree.exceptions.RemoteReadException;

import java.io.IOException;

public class ShutdownBankServerThread extends NotificationQueue<ShutdownBankServerNotification> {
    public ShutdownBankServerThread(int taskSize) {
        super(taskSize);
    }

    public void run() {
        while(!this.isShutdown()) {
            while(!this.isEmpty()) {
                try {
                    ShutdownBankServerNotification notification = this.getNotification();
                    BankServer.CS().stop(3000L);
                    this.disposeMessage(notification);
                } catch (IOException | ClassNotFoundException | RemoteReadException | InterruptedException var4) {
                    var4.printStackTrace();
                }
            }

            try {
                this.holdOn(1000L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }

    }
}
