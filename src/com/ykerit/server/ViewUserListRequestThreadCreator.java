package com.ykerit.server;

import com.ykerit.message.ViewUserListRequest;
import com.ykerit.message.ViewUserListResponse;
import com.ykerit.message.ViewUserListStream;
import org.greatfree.concurrency.interactive.RequestThreadCreatable;

public class ViewUserListRequestThreadCreator implements RequestThreadCreatable<ViewUserListRequest,
        ViewUserListStream, ViewUserListResponse, ViewUserListRequestThread> {
    @Override
    public ViewUserListRequestThread createRequestThreadInstance(int size) {
        return new ViewUserListRequestThread(size);
    }
}
