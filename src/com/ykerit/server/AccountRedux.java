package com.ykerit.server;

import com.ykerit.message.UserAccount;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRedux {
    private ConcurrentHashMap<String, UserAccount> accounts = new ConcurrentHashMap<>();

    private static AccountRedux instance = new AccountRedux();

    private AccountRedux() {

    }

    public static AccountRedux CS() {
        if (instance == null) {
            instance = new AccountRedux();
            return instance;
        } else {
            return instance;
        }
    }

    public boolean isAccountExisted(String userKey) {
        return this.accounts.containsKey(userKey);
    }

    public void add(UserAccount account) {
        if (!this.accounts.containsKey(account.getUsername())) {
            this.accounts.put(account.getUsername(), account);
        }

    }

    public Collection<UserAccount> getAllAccounts() {
        return this.accounts.values();
    }

    public UserAccount getAccount(String key) {
        return this.accounts.containsKey(key) ? (UserAccount) this.accounts.get(key) : null;
    }
}
