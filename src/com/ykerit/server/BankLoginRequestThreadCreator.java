package com.ykerit.server;

import com.ykerit.message.BankLoginRequest;
import com.ykerit.message.BankLoginResponse;
import com.ykerit.message.BankLoginStream;
import org.greatfree.concurrency.interactive.RequestThreadCreatable;

public class BankLoginRequestThreadCreator implements RequestThreadCreatable<BankLoginRequest, BankLoginStream,
        BankLoginResponse, BankLoginRequestThread> {
    @Override
    public BankLoginRequestThread createRequestThreadInstance(int size) {
        return new BankLoginRequestThread(size);
    }
}
