package com.ykerit.server;

import com.ykerit.message.CheckAccountRequest;
import com.ykerit.message.CheckAccountResponse;
import com.ykerit.message.CheckAccountStream;
import com.ykerit.message.UserAccount;
import org.greatfree.concurrency.interactive.RequestQueue;
import org.greatfree.data.ServerConfig;

import java.io.IOException;

public class CheckAccountRequestThread extends RequestQueue<CheckAccountRequest, CheckAccountStream, CheckAccountResponse> {

    public CheckAccountRequestThread(int requestQueueSize) {
        super(requestQueueSize);
    }

    @Override
    public void run() {

        CheckAccountStream request;
        CheckAccountResponse response;

        while (!isShutdown()) {
            while (!isEmpty()) {
                request = this.getRequest();

                System.out.println("request is received " + request.getMessage().getUsername());
                UserAccount userAccount = AccountRedux.CS().getAccount(request.getMessage().getUsername());

                response = new CheckAccountResponse(userAccount.getUsername()
                        + " balance is " + userAccount.getAccountBalance());

                try {
                    this.respond(request.getOutStream(), request.getLock(), response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                disposeMessage(request, response);
            }

            try {
                this.holdOn(ServerConfig.REQUEST_THREAD_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
