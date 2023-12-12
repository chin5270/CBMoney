package com.example.cbmoney.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "income_table",
        foreignKeys = @ForeignKey(
                entity = AccountEntity.class,
                parentColumns = "accountName",
                childColumns = "account",
                onUpdate = ForeignKey.CASCADE, // 在父表（AccountEntity）中更新時，相應的子表（ExpenseEntity）也進行更新
                onDelete = ForeignKey.CASCADE //
        ))

public class IncomeEntity extends TransactionEntity{
    @PrimaryKey(autoGenerate = true) // 作為主鍵，並指示 Room 自動生成主鍵的值
    private int id;
    private String category;
    private String description;
    private int money;
    private String account;
    private int year;
    private int month;
    private int day;

    public IncomeEntity(String category, String description, int money, String account, int year, int month, int day){
        super(category, description, money, account, year, month, day);
        this.category = category;
        this.description = description;
        this.money = money;
        this.account = account;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getMoney() {
        return money;
    }

    @Override
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
