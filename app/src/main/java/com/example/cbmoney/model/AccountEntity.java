package com.example.cbmoney.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table" ,indices = {@Index(value = "accountName", unique = true)})
public class AccountEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;


    private String accountName;
    private int initialBalance;

    public AccountEntity(String accountName,int initialBalance) {
        this.accountName = accountName;
        this.initialBalance = initialBalance;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }



    public int getInitialBalance() {
        return initialBalance;
    }
}
