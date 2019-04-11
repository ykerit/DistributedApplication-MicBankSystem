package com.ykerit.Admin;

import org.greatfree.exceptions.RemoteReadException;

import java.io.IOException;
import java.util.Scanner;

public class StartBankAdminClient {
    public static void main(String[] args) throws IOException, InterruptedException, RemoteReadException, ClassNotFoundException {
        int option = -1;

        Scanner in = new Scanner(System.in);
        System.out.println("welcome to Bank Admin!");

        BankAdminClientUI.CS().init();

        while (option != 0) {
            BankAdminClientUI.CS().printMenu();
            String optionStr = in.nextLine();

            option = Integer.parseInt(optionStr);
            System.out.println("Your choice: " + option);
            BankAdminClientUI.CS().send(option);
        }
        BankAdminClientUI.CS().dispose();
    }
}
