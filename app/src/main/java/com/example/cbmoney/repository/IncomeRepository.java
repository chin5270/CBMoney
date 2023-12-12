package com.example.cbmoney.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cbmoney.model.IncomeEntity;
import com.example.cbmoney.model.TransactionEntity;

import java.util.List;

public class IncomeRepository {
    private IncomeDao incomeDao;
    private LiveData<Integer> TotalAccountIncome;
    private LiveData<List<IncomeEntity>> AllIncomesForDay;

    public IncomeRepository(Application application){
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        incomeDao = database.incomeDao();
    }

    public void setAllIncomesForDay(int year, int month,int day) {
        AllIncomesForDay = incomeDao.getAllIncomesForDay(year, month,day);
    }

    public LiveData<List<IncomeEntity>> getAllIncomesForDay(){
        return AllIncomesForDay;
    }

    public void setTotalAccountIncome(String account){
        TotalAccountIncome = incomeDao.getTotalAccountIncome(account);
    }

    public LiveData<Integer> getTotalAccountIncome(){
        return TotalAccountIncome;
    }

    public void insert(IncomeEntity income){
        new InsertIncomeAsynctask(incomeDao).execute(income);
    }
    public void update(IncomeEntity income){
        new UpdateIncomeAsynctask(incomeDao).execute(income);
    }
    public void delete(IncomeEntity income){
        new DeleteIncomeAsynctask(incomeDao).execute(income);
    }

    private static class InsertIncomeAsynctask extends AsyncTask<IncomeEntity,Void,Void>{
        private IncomeDao incomeDao;
        private InsertIncomeAsynctask(IncomeDao incomeDao){
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(IncomeEntity... incomes) {
            incomeDao.insert(incomes[0]);
            return null;
        }
    }
    private static class UpdateIncomeAsynctask extends AsyncTask<IncomeEntity,Void,Void>{
        private IncomeDao incomeDao;
        private UpdateIncomeAsynctask(IncomeDao incomeDao){
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(IncomeEntity... incomes) {
            incomeDao.update(incomes[0]);
            return null;
        }
    }
    private static class DeleteIncomeAsynctask extends AsyncTask<IncomeEntity,Void,Void>{
        private IncomeDao incomeDao;
        private DeleteIncomeAsynctask(IncomeDao incomeDao){
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(IncomeEntity... incomes) {
            incomeDao.delete(incomes[0]);
            return null;
        }
    }
}
