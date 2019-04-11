package com.ykerit.message;

import java.io.Serializable;

public class UserRecord implements Serializable {
    private String type;
    private int amount;

    public UserRecord(String type, int amount){
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
