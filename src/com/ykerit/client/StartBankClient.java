package com.ykerit.client;


import com.ykerit.message.BankLoginResponse;
import org.greatfree.exceptions.RemoteReadException;

import java.io.IOException;
import java.util.Scanner;

public class StartBankClient {
    public static void main(String[] args) throws IOException, InterruptedException, RemoteReadException, ClassNotFoundException {
        int option = -1;

        Scanner in = new Scanner(System.in);
        System.out.println("welcome to Bank!");
        System.out.println("Tell me your user name: ");
        String username = in.nextLine();
        BankClientUI.CS().init(username, 0);
        BankLoginResponse response = BankClientUI.CS().bankLoginRequest(username);

        if (response.getStatus()) {
            while (option != 0) {
                BankClientUI.CS().printMenu(username);
                String optionStr = in.nextLine();

                option = Integer.parseInt(optionStr);
                System.out.println("Your choice: " + option);
                BankClientUI.CS().send(option, username);
            }
        } else {
            System.out.println("login error");
        }

        BankClientUI.CS().dispose();
    }
}
