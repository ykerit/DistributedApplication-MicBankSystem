package com.ykerit.server;

import com.ykerit.message.UserAccount;
import com.ykerit.message.ViewUserListRequest;
import com.ykerit.message.ViewUserListResponse;
import com.ykerit.message.ViewUserListStream;
import org.greatfree.concurrency.interactive.RequestQueue;
import org.greatfree.data.ServerConfig;

import java.io.IOException;
import java.util.Collection;

public class ViewUserListRequestThread extends RequestQueue<ViewUserListRequest, ViewUserListStream, ViewUserListResponse> {

    public ViewUserListRequestThread(int requestQueueSize) {
        super(requestQueueSize);
    }

    @Override
    public void run() {
        ViewUserListStream request;
        ViewUserListResponse response;

        while (!isShutdown()) {
            for (; !isEmpty(); disposeMessage(request, response)) {
                request = this.getRequest();
                System.out.println("list request " + request.getMessage().getQuery());
                Collection<UserAccount> collection = AccountRedux.CS().getAllAccounts();

                StringBuilder str = new StringBuilder();
                str.append("current user: " + collection.size() + "\n");
                for (UserAccount item:collection
                     ) {
                    str.append("name: " + item.getUsername() +" balance: "+ item.getAccountBalance() + "\n");
                }

                response = new ViewUserListResponse(new String(str));

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
