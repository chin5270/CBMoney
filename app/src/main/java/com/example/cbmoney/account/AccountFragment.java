package com.example.cbmoney.account;

import android.content.Intent;
import android.os.Bundle;
import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cbmoney.databinding.FragmentAccountBinding;

import com.example.cbmoney.model.AccountEntity;


import java.util.List;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private AccountViewModel accountViewModel;
    private RecyclerView accountRecyclerView;
    private AccountAdapter accountAdapter;
    public static final int ADD_ACCOUNT_REQUEST = 1;
    public static final int EDIT_ACCOUNT_REQUEST = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentAccountBinding.inflate(inflater, container, false);

       setAccountAdapter();
       setAccountViewModel();


       goToAddCardActivity();
       swipeAccountToDelete(accountAdapter,accountRecyclerView);
       updateAccount(accountAdapter);
       return  binding.getRoot();
    }


    private void setAccountAdapter(){
        accountRecyclerView  = binding.recyclerViewAccount;
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accountRecyclerView.setHasFixedSize(true);

        accountAdapter = new AccountAdapter();
        accountRecyclerView.setAdapter(accountAdapter);
    }
    private void setAccountViewModel(){
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getAllAccounts().observe(getViewLifecycleOwner(), new Observer<List<AccountEntity>>() {
            @Override
            public void onChanged(List<AccountEntity> accounts) {
                accountAdapter.setAccounts(accounts);
            }
        });
    }

    //  按下加號，頁面跳到AddCardActivity
    private void goToAddCardActivity(){
        //       startActivityForResult 是一種啟動另一個 Activity 並期望在該 Activity 完成後收到結果的方法。
//       另一個Activity寫下RESULT_OK 表示操作成功，你也可以使用 RESULT_CANCELED 表示操作取消。
        binding.buttonAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountData = new Intent(getActivity(), AddCardActivity.class);

                // 直接創建一個新的 Bundle

                startActivityForResult(accountData, ADD_ACCOUNT_REQUEST);
            }
        });
    }

    // 更新帳戶資訊，將當筆資料傳到AddCardActivity
    public void updateAccount(AccountAdapter accountAdapter){
        // 當項目被點擊時，創建一個新的 Intent，將相應的 ExpenseEntity 對象的信息放入 Intent 中，
        // 然後啟動 AddEditNoteActivity 以編輯該筆資料，
        // 需要確保在 TransactionAdapter 中添加相應的方法
        accountAdapter.setOnItemClickListener(new AccountAdapter.OnIttemClickListner() {
            @Override
            public void onItemClick(AccountEntity account) {
                Intent accountData = new Intent(getActivity(), AddCardActivity.class);
                Bundle accountBundle2 = new Bundle();
                accountBundle2.putString("Extra_account",account.getAccountName());
                accountBundle2.putInt("Extra_initialBalance",account.getInitialBalance());
                accountBundle2.putInt("Extra_id",account.getId());
                accountData.putExtras(accountBundle2);

                startActivityForResult(accountData,EDIT_ACCOUNT_REQUEST);
            }
        });
    }

    // 向左滑刪除帳戶
    private void swipeAccountToDelete(AccountAdapter accountAdapter, RecyclerView recyclerView){
        // ItemTouchHelper 類，這是一個用於處理 RecyclerView 中項目拖動和滑動的幫助類
        // onMove 方法，用於處理拖動事件。在這裡，它返回 false，表示不處理拖動操作。
        // onSwiped 方法，用於處理滑動事件。當用戶滑動 RecyclerView 中的項目時，此方法將被調用。
        // ViewHolder 是一個用於暫存 item view 的對象，它與 RecyclerView 中的特定位置相關聯。
        // .attachToRecyclerView(recyclerView);：將創建的 ItemTouchHelper
        // 附加到 RecyclerView 上，這樣它就可以監聽拖動和滑動事件，並呼叫相應的回調方法。
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // AccountAdapter 中，getExpenseAt 實際上是調用了 getItem(position) 方法，這是 ListAdapter 提供的方法。
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                accountViewModel.delete(accountAdapter.getAcountAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(),"account deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    // 拿回從 AddCardActivity 拿回的資料，用 viewmodel 新增或是更新資料
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_ACCOUNT_REQUEST && resultCode==RESULT_OK){
            Bundle accountBundle = data.getExtras();
            String account = accountBundle.getString("Extra_account");
            int initialBalance = accountBundle.getInt("Extra_initialBalance");

            accountViewModel.insert(new AccountEntity(account,initialBalance));
            Toast.makeText(getContext(),"account saved",Toast.LENGTH_SHORT).show();
        }else if(requestCode==EDIT_ACCOUNT_REQUEST && resultCode==RESULT_OK){
            Bundle accountBundle = data.getExtras();
            int id = accountBundle.getInt("Extra_id",-1);
            if(id==-1){
                Toast.makeText(getContext(),"account cannot be updated",Toast.LENGTH_SHORT).show();
                return;
            }
            String account = accountBundle.getString("Extra_account");
            int initialBalance = accountBundle.getInt("Extra_initialBalance");

            AccountEntity account1 = new AccountEntity(account,initialBalance);
            account1.setId(id);
            accountViewModel.update(account1);
            Toast.makeText(getContext(),"account updated",Toast.LENGTH_SHORT).show();

        } else{
            Toast.makeText(getContext(),"account not  saved",Toast.LENGTH_SHORT).show();
        }
    }
}