package com.example.cbmoney.account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cbmoney.model.AccountEntity;
import com.example.cbmoney.model.ExpenseEntity;
import com.example.cbmoney.model.IncomeEntity;
import com.example.cbmoney.repository.AccountRepository;
import com.example.cbmoney.repository.ExpenseRepository;
import com.example.cbmoney.repository.IncomeRepository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;
    private IncomeRepository incomeRepository;
    private ExpenseRepository expenseRepository;
    private LiveData<List<AccountEntity>> allAccounts;
    private LiveData<List<ExpenseEntity>> allExpenses;
    private LiveData<List<IncomeEntity>> allIncomes;


    public AccountViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
        allAccounts = accountRepository.getAllAcounts();
        expenseRepository = new ExpenseRepository(application);
        allExpenses = expenseRepository.getAllExpenses();
        incomeRepository = new IncomeRepository(application);
        allIncomes = incomeRepository.getAllIncomes();
    }

    public void insert(AccountEntity account){
        accountRepository.insert(account);
    }
    public void update(AccountEntity account){
        accountRepository.update(account);
    }
    public void delete(AccountEntity account){
        accountRepository.delete(account);
    }

    public LiveData<List<AccountEntity>> getAllAccounts(){
        return allAccounts;
    }

    public  LiveData<List<ExpenseEntity>> getAllExpenses(){
        return allExpenses;
    }

    public LiveData<List<IncomeEntity>> getAllIncomes() {
        return allIncomes;
    }
}
