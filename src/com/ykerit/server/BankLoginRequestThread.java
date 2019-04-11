package com.ykerit.server;

import com.ykerit.message.*;
import org.greatfree.concurrency.interactive.RequestQueue;
import org.greatfree.data.ServerConfig;

import java.io.IOException;

public class BankLoginRequestThread extends RequestQueue<BankLoginRequest, BankLoginStream, BankLoginResponse> {
    public BankLoginRequestThread(int requestQueueSize) {
        super(requestQueueSize);
    }

    @Override
    public void run() {
        BankLoginStream request;
        BankLoginResponse response;
        while (!isShutdown()) {
            for (; !isEmpty(); disposeMessage(request, response)) {
                request = this.getRequest();
                if (!request.getMessage().getUsername().equals("")) {
                    UserAccount userAccount = new UserAccount(request.getMessage().getUsername(),
                            0, new UserRecord("null", 0));
                    AccountRedux.CS().add(userAccount);
                    response = new BankLoginResponse(true);
                } else {
                    response = new BankLoginResponse(false);
                }
                try {
                    this.respond(request.getOutStream(), request.getLock(), response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            try {
                this.holdOn(ServerConfig.REQUEST_DISPATCHER_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
