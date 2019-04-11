package com.ykerit.message;

import org.greatfree.client.OutMessageStream;

import java.io.ObjectOutputStream;
import java.util.concurrent.locks.Lock;

public class CheckAccountStream extends OutMessageStream<CheckAccountRequest> {
    public CheckAccountStream(ObjectOutputStream out, Lock lock, CheckAccountRequest message) {
        super(out, lock, message);
    }
}
