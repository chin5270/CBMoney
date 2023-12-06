package com.example.cbmoney.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cbmoney.model.AccountEntity;

import java.util.List;

public class AccountRepository {
    private AccountDao accountDao;
    private LiveData<List<AccountEntity>> allAcounts;
    public AccountRepository(Application application){
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        accountDao = database.accountDao();
        allAcounts = accountDao.getAllAcounts();
    }

    public void insert(AccountEntity account){
        new InsertAccountAsyncTask(accountDao).execute(account);
    }
    public void update(AccountEntity account){
        new UpdateAccountAsyncTask(accountDao).execute(account);
    }
    public void delete(AccountEntity account){
        new DeleteAccountAsyncTask(accountDao).execute(account);
    }

    public LiveData<List<AccountEntity>> getAllAcounts(){
        return allAcounts;
    }

    private static class InsertAccountAsyncTask extends AsyncTask<AccountEntity,Void,Void>{
        private AccountDao accountDao;
        private InsertAccountAsyncTask(AccountDao accountDao){
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(AccountEntity... accountEntities) {
            accountDao.inert(accountEntities[0]);
            return null;
        }
    }

    private static class UpdateAccountAsyncTask extends AsyncTask<AccountEntity,Void,Void>{
        private AccountDao accountDao;
        private UpdateAccountAsyncTask(AccountDao accountDao){
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(AccountEntity... accountEntities) {
            accountDao.update(accountEntities[0]);
            return null;
        }
    }
    private static class DeleteAccountAsyncTask extends AsyncTask<AccountEntity,Void,Void>{
        private AccountDao accountDao;
        private DeleteAccountAsyncTask(AccountDao accountDao){
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(AccountEntity... accountEntities) {
            accountDao.delete(accountEntities[0]);
            return null;
        }
    }
}
