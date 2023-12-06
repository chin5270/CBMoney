package com.example.cbmoney.account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cbmoney.model.AccountEntity;
import com.example.cbmoney.repository.AccountRepository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;
    private LiveData<List<AccountEntity>> allAccounts;


    public AccountViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
        allAccounts = accountRepository.getAllAcounts();
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
}
