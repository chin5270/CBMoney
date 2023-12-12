package com.example.cbmoney.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "transaction_table",
        foreignKeys = @ForeignKey(
                entity = AccountEntity.class,
                parentColumns = "accountName",
                childColumns = "account",
                onUpdate = ForeignKey.CASCADE, // 在父表（AccountEntity）中更新時，相應的子表（ExpenseEntity）也進行更新
                onDelete = ForeignKey.CASCADE //
        ))
public class TransactionEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String category;
    private String description;
    private int money;
    private String account;
    private int year;
    private int month;
    private int day;

    public TransactionEntity(String category, String description, int money, String account, int year, int month, int day) {
        this.category = category;
        this.description = description;
        this.money = money;
        this.account = account;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getMoney() {
        return money;
    }

    public String getAccount() {
        return account;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
