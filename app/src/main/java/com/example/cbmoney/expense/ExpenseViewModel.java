package com.example.cbmoney.expense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cbmoney.model.ExpenseEntity;
import com.example.cbmoney.model.TransactionEntity;
import com.example.cbmoney.repository.ExpenseRepository;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;
    private LiveData<Integer> totalExpenseForDay;
    private LiveData<Integer> categoryExpenseForMonth;

    private LiveData<List<ExpenseEntity>>  allExpensesForDay;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        categoryExpenseForMonth = new MutableLiveData<>();

    }




    public void setTotalExpenseForDay(int year, int month,int day) {
        repository.setTotalExpenseForDay(year, month,day);
    }

    public void setCategoryExpenseForMonth(int year, int month, String category) {
        repository.setCategoryExpenseForMonth(year, month, category);
    }



    public void setAllExpensesForDay(int year, int month,int day) {
        repository.setAllExpensesForDay(year, month,day);
    }


    public LiveData<Integer> getTotalExpenseForDay() {
        totalExpenseForDay = repository.getTotalExpenseForDay();
        return totalExpenseForDay;
    }

    public LiveData<Integer> getCategoryExpenseForMonth() {
        categoryExpenseForMonth = repository.getCategoryExpenseForMonth();
        return categoryExpenseForMonth;
    }


    public LiveData<List<ExpenseEntity>> getAllExpensesForDay() {
        allExpensesForDay = repository.getAllExpensesForDay();
        return allExpensesForDay;
    }
    public void insert(ExpenseEntity expense){
        repository.insert(expense);
    }
    public void update(ExpenseEntity expense){
        repository.update(expense);
    }
    public void delete(ExpenseEntity expense){
        repository.delete(expense);
    }
}

