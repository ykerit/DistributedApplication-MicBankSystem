package com.ykerit.message;

import org.greatfree.client.OutMessageStream;

import java.io.ObjectOutputStream;
import java.util.concurrent.locks.Lock;

public class BankLoginStream extends OutMessageStream<BankLoginRequest> {
    public BankLoginStream(ObjectOutputStream out, Lock lock, BankLoginRequest message) {
        super(out, lock, message);
    }
}
