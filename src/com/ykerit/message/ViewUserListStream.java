package com.ykerit.message;

import org.greatfree.client.OutMessageStream;

import java.io.ObjectOutputStream;
import java.util.concurrent.locks.Lock;

public class ViewUserListStream extends OutMessageStream<ViewUserListRequest> {
    public ViewUserListStream(ObjectOutputStream out, Lock lock, ViewUserListRequest message) {
        super(out, lock, message);
    }
}
