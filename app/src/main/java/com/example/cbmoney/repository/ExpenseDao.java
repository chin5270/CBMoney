package com.example.cbmoney.repository;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cbmoney.model.ExpenseEntity;
import com.example.cbmoney.model.TransactionEntity;

import java.util.List;
@Dao
public interface ExpenseDao {

    @Insert
    void insert(ExpenseEntity expense);
    @Update
    void update(ExpenseEntity expense);
    @Delete
    void delete(ExpenseEntity expense);

    // 這種方式允許你使用 SQL 語法來定義自定義的數據庫操作，
    // 同時保持介面的抽象性。Room 負責在背後生成實現，使得這些方法可以直接與數據庫進行交互。
    @Query("DELETE FROM expense_table")
    void deleteAllNotes();

    // 當數據庫中的筆記發生變化時，與這個方法相關聯的觀察者會收到通知
    // :year 和 :month 是占位符，表示這是一個帶有參數的查詢
    // 查詢某年某月的總花費
    @Query("SELECT SUM(money) FROM expense_table WHERE year = :year AND month = :month")
    LiveData<Integer> getTotalExpenseForMonth(int year, int month);

    // 查詢某個 account 的總花費
    @Query("SELECT SUM(money) FROM expense_table WHERE account = :account")
    LiveData<Integer> getTotalAccountExpense(String account);

    // 查詢某年某月的總花費
    @Query("SELECT SUM(money) FROM expense_table WHERE year = :year AND month = :month AND day = :day")
    LiveData<Integer> getTotalExpenseForDay(int year, int month,int day);

    // 查詢某年某月，他某個類別的總花費
    @Query("SELECT SUM(money) FROM expense_table WHERE year = :year AND month = :month AND category = :category")
    LiveData<Integer> getCategoryExpenseForMonth(int year, int month,String category);

    // 查詢某年某月的所有筆資料，並按照day排序，由大到小
    @Query("SELECT * FROM expense_table WHERE year = :year AND month = :month ORDER BY day DESC" )
    LiveData<List<ExpenseEntity>> getAllExpensesForMonth(int year, int month);

    // 查詢某年某月某日的所有筆資料，並按照day排序，由大到小
    @Query("SELECT * FROM expense_table WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC" )
    LiveData<List<ExpenseEntity>> getAllExpensesForDay(int year, int month, int day);

}
