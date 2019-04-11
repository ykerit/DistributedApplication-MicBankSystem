package com.ykerit.server;


import org.greatfree.data.ServerConfig;
import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.TerminateSignal;

import java.io.IOException;

public class StartBankServer {
    public StartBankServer() {
    }

    public static void main(String[] args) {
        System.out.println("Bank server starting up ...");
        try {
            BankServer.CS().start();
        } catch (IOException | ClassNotFoundException | RemoteReadException e) {
            e.printStackTrace();
        }
        System.out.println("BankServer start ... ");

        while (!TerminateSignal.SIGNAL().isTerminated()) {
            try {
                Thread.sleep(ServerConfig.TERMINATE_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
