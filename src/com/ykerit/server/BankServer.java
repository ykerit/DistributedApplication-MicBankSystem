package com.ykerit.server;

import com.ykerit.BankConfig;
import org.greatfree.data.ServerConfig;
import org.greatfree.dip.p2p.RegistryConfig;
import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.server.CSServer;
import org.greatfree.util.TerminateSignal;

import java.io.IOException;

public class BankServer {
    private CSServer<BankServerDispatcher> server;

    private BankServer() {

    }

    // Single Pattern
    private static BankServer instance = new BankServer();

    public static BankServer CS() {
        if (instance == null)
        {
            instance = new BankServer();
            return instance;
        }
        else
        {
            return instance;
        }
    }

    public void stop(long timeout) throws ClassNotFoundException, IOException, InterruptedException, RemoteReadException
    {
        TerminateSignal.SIGNAL().setTerminated();
        this.server.stop(timeout);
    }
    public void start() throws IOException, ClassNotFoundException, RemoteReadException
    {
        this.server = new CSServer.CSServerBuilder<BankServerDispatcher>().port(BankConfig.BANK_SERVER_PORT)
                .listenerCount(ServerConfig.LISTENING_THREAD_COUNT)
                .dispatcher(new BankServerDispatcher(RegistryConfig.DISPATCHER_THREAD_POOL_SIZE,
                        RegistryConfig.DISPATCHER_THREAD_POOL_KEEP_ALIVE_TIME,
                        RegistryConfig.SCHEDULER_THREAD_POOL_SIZE,
                        RegistryConfig.SCHEDULER_THREAD_POOL_KEEP_ALIVE_TIME))
                .build();

        this.server.start();
    }
}
