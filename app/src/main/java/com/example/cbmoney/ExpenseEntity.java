package com.example.cbmoney;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "expense_table") //  Room 創建一個名為 "note_table" 的表格
public class ExpenseEntity {
        // column
        @PrimaryKey(autoGenerate = true) // 作為主鍵，並指示 Room 自動生成主鍵的值
        private int id;
        private String category;
        private String description;
        private int expense;
        private String account;
        private int year;
        private int month;
        private int day;



    public ExpenseEntity(String category, String description, int expense, String account, int year, int month, int day){
            this.category = category;
            this.description = description;
            this.expense = expense;
            this.account = account;
            this.year = year;
            this.month = month;
            this.day = day;

        }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getExpense() {
        return expense;
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
