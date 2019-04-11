package com.ykerit.server;

import com.ykerit.message.CheckAccountRequest;
import com.ykerit.message.CheckAccountResponse;
import com.ykerit.message.CheckAccountStream;
import org.greatfree.concurrency.interactive.RequestThreadCreatable;

public class CheckAccountRequestThreadCreator implements
        RequestThreadCreatable<CheckAccountRequest, CheckAccountStream, CheckAccountResponse, CheckAccountRequestThread> {

    @Override
    public CheckAccountRequestThread createRequestThreadInstance(int size) {
        return new CheckAccountRequestThread(size);
    }
}
