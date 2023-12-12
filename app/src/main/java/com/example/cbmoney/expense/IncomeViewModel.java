package com.example.cbmoney.expense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cbmoney.model.IncomeEntity;
import com.example.cbmoney.model.TransactionEntity;
import com.example.cbmoney.repository.IncomeRepository;

import java.util.List;

public class IncomeViewModel extends AndroidViewModel {
    private IncomeRepository repository;
    private LiveData<Integer> TotalAccountIncome;
    private LiveData<List<IncomeEntity>> AllIncomesForDay;
    public IncomeViewModel(@NonNull Application application) {
        super(application);
        repository = new IncomeRepository(application);
        TotalAccountIncome = new MutableLiveData<>();  // 初始化為 MutableLiveData
        AllIncomesForDay = new MutableLiveData<>();


    }

    public void setAllIncomesForDay(int year, int month,int day) {
        repository.setAllIncomesForDay(year, month, day);
    }

    public LiveData<List<IncomeEntity>> getAllIncomesForDay(){
        AllIncomesForDay = repository.getAllIncomesForDay();
        return AllIncomesForDay;
    }

    public void setTotalAccountIncome(String account){
        repository.setTotalAccountIncome(account);
    }

    public LiveData<Integer> getTotalAccountIncome(){
        return TotalAccountIncome;
    }
    public void insert(IncomeEntity income){
        repository.insert(income);
    }
    public void update(IncomeEntity income){
        repository.update(income);
    }
    public void delete(IncomeEntity income){
        repository.delete(income);
    }
}
