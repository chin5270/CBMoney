package com.example.cbmoney.expense;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.cbmoney.model.ExpenseEntity;
import com.example.cbmoney.model.IncomeEntity;
import com.example.cbmoney.model.TransactionEntity;
import com.example.cbmoney.repository.ExpenseRepository;
import com.example.cbmoney.repository.IncomeRepository;

public class TransactionViewModel extends AndroidViewModel {
    private ExpenseRepository expenseRepository;
    private IncomeRepository incomeRepository;

    public TransactionViewModel(Application application){
        super(application);
        expenseRepository = new ExpenseRepository(application);
        incomeRepository = new IncomeRepository(application);

    }
    public void delete(TransactionEntity transaction) {
        // 在這裡處理通用的 TransactionEntity，你可能需要判斷是支出還是收入
        if (transaction instanceof ExpenseEntity) {
            ExpenseEntity expense = (ExpenseEntity) transaction;
            expenseRepository.delete(expense);

        } else if (transaction instanceof IncomeEntity) {
            IncomeEntity income = (IncomeEntity) transaction;
           incomeRepository.delete(income);
        }
    }
}
