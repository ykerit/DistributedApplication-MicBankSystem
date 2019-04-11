package com.ykerit.Admin;

import com.ykerit.BankConfig;
import com.ykerit.message.ShutdownBankServerNotification;
import com.ykerit.message.ViewUserListRequest;
import com.ykerit.message.ViewUserListResponse;
import org.greatfree.client.FreeClientPool;
import org.greatfree.client.RemoteReader;
import org.greatfree.client.SyncRemoteEventer;
import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.NodeID;

import java.io.IOException;
import java.util.Calendar;

public class BankAdminClientUI {
    private static BankAdminClientUI instance = new BankAdminClientUI();

    private FreeClientPool clientPool;
    private SyncRemoteEventer<ShutdownBankServerNotification> syncShutdownEventer;

    public static BankAdminClientUI CS() {
        if (instance == null) {
            instance = new BankAdminClientUI();
            return instance;
        } else {
            return instance;
        }
    }

    public void init() {
        this.clientPool = new FreeClientPool(10);
        this.syncShutdownEventer = new SyncRemoteEventer<>(this.clientPool);
        RemoteReader.REMOTE().init(10);
    }

    public void dispose() throws IOException {
        this.clientPool.dispose();
    }

    public void printMenu() {
        System.out.println("\ntoday is " + Calendar.getInstance().getTime());
        System.out.println("\n========== Menu Head ===========");
        System.out.println("\t1) View User List");
        System.out.println("\t2) Stop Bank Server");
        System.out.println("\t0) Quit");
        System.out.println("========== Menu Tail ===========\n");
        System.out.println("Input an option:");
    }

    public void send(int option) throws RemoteReadException, IOException, ClassNotFoundException, InterruptedException {
        switch(option) {
            case 1:
                ViewUserListResponse viewUserListResponse = viewUserListResponse();
                System.out.println(viewUserListResponse.getResponse());
                break;
            case 2:
                sendShutdownNotification();
                break;
            default:
                break;

        }

    }

    private void sendShutdownNotification() throws IOException, InterruptedException {
        this.syncShutdownEventer.notify(BankConfig.BANK_SERVER_ADDRESS, BankConfig.BANK_SERVER_PORT,
                new ShutdownBankServerNotification());
    }

    private ViewUserListResponse viewUserListResponse() throws IOException, RemoteReadException, ClassNotFoundException {
        return (ViewUserListResponse) RemoteReader.REMOTE().read(NodeID.DISTRIBUTED().getKey(),
                BankConfig.BANK_SERVER_ADDRESS, BankConfig.BANK_SERVER_PORT, new ViewUserListRequest("get user list"));
    }
}
