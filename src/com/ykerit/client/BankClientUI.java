package com.ykerit.client;

import com.ykerit.BankConfig;
import com.ykerit.message.*;
import org.greatfree.client.FreeClientPool;
import org.greatfree.client.RemoteReader;
import org.greatfree.client.SyncRemoteEventer;
import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.NodeID;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class BankClientUI {
    private static BankClientUI instance = new BankClientUI();

    private FreeClientPool clientPool;
    private SyncRemoteEventer<DepositNotification> syncDepositEventer;
    private SyncRemoteEventer<WithDrawNotification> syncWithDrawEventer;
    private Scanner in;

    private BankClientUI() {
        this.in = new Scanner(System.in);
    }

    public static BankClientUI CS() {
        if (instance == null) {
            instance = new BankClientUI();
            return instance;
        } else {
            return instance;
        }
    }

    public void dispose() throws IOException {
        this.clientPool.dispose();
        this.syncDepositEventer.dispose();
        this.syncWithDrawEventer.dispose();
        RemoteReader.REMOTE().shutdown();
    }

    public void init(String username, int accountBalance) {
        this.clientPool = new FreeClientPool(10);
        this.syncWithDrawEventer = new SyncRemoteEventer<>(this.clientPool);
        this.syncDepositEventer = new SyncRemoteEventer<>(this.clientPool);
        RemoteReader.REMOTE().init(10);
    }


    public void printMenu(String username) {
        System.out.println("\nHi!" + username + "  today is " + Calendar.getInstance().getTime());
        System.out.println("\n========== Menu Head ===========");
        System.out.println("\t1) Deposit");
        System.out.println("\t2) Withdraw");
        System.out.println("\t3) Check Account");
        System.out.println("\t0) Quit");
        System.out.println("========== Menu Tail ===========\n");
        System.out.println("Input an option:");
    }

    public void send(int option, String username) throws IOException, InterruptedException, RemoteReadException, ClassNotFoundException {
        switch(option) {
            case 1:
                sendDepositNotification(username);
                break;
            case 2:
                sendWithDrawNotification(username);
                break;
            case 3:
                CheckAccountResponse response = CheckAccountRequest(username);
                System.out.println(response.getResponse());
                break;
            default:
                break;

        }

    }
    private void sendDepositNotification(String username) throws IOException, InterruptedException {
        System.out.println("Please enter the amount of deposit:");
        String money = this.in.nextLine();
        this.syncDepositEventer.notify("127.0.0.1", BankConfig.BANK_SERVER_PORT,
                new DepositNotification(username, Integer.parseInt(money)));
    }

    private void sendWithDrawNotification(String username) throws IOException, InterruptedException {
        System.out.println("Please enter the amount of withdrawal:");
        String money = this.in.nextLine();
        this.syncWithDrawEventer.notify(BankConfig.BANK_SERVER_ADDRESS, BankConfig.BANK_SERVER_PORT,
                new WithDrawNotification(username, Integer.parseInt(money)));
    }

    private CheckAccountResponse CheckAccountRequest(String username) throws IOException, RemoteReadException, ClassNotFoundException {
        return (CheckAccountResponse) RemoteReader.REMOTE().read(NodeID.DISTRIBUTED().getKey(),
                BankConfig.BANK_SERVER_ADDRESS, BankConfig.BANK_SERVER_PORT, new CheckAccountRequest(username));
    }

    public BankLoginResponse bankLoginRequest(String username) throws IOException, RemoteReadException, ClassNotFoundException {
        return (BankLoginResponse) RemoteReader.REMOTE().read(NodeID.DISTRIBUTED().getKey(),
                BankConfig.BANK_SERVER_ADDRESS, BankConfig.BANK_SERVER_PORT, new BankLoginRequest(username));
    }
}
