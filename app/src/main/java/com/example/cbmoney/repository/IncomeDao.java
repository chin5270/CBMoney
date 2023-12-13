package com.example.cbmoney.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cbmoney.model.ExpenseEntity;
import com.example.cbmoney.model.IncomeEntity;
import com.example.cbmoney.model.TransactionEntity;

import java.util.List;

@Dao
public interface IncomeDao {
    @Insert
    void insert(IncomeEntity income);
    @Update
    void update(IncomeEntity income);
    @Delete
    void delete(IncomeEntity income);


    // 查詢某年某月某日的所有筆資料，並按照day排序，由大到小
    @Query("SELECT * FROM income_table WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC" )
    LiveData<List<IncomeEntity>> getAllIncomesForDay(int year, int month, int day);

    @Query("SELECT * FROM income_table")
    LiveData<List<IncomeEntity>> getAllIncomes();
}
