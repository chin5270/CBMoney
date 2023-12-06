package com.example.cbmoney.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.cbmoney.model.AccountEntity;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert
    void inert(AccountEntity account);

    @Update
    void update(AccountEntity account);
    @Delete
    void delete(AccountEntity account);

    @Query("SELECT * FROM account_table")
    LiveData<List<AccountEntity>> getAllAcounts();

}
