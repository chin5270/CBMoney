package com.example.cbmoney.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.cbmoney.R;
import com.example.cbmoney.databinding.ActivityAddCardBinding;


public class AddCardActivity extends AppCompatActivity {
    ActivityAddCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 設定叉叉的圖是在左上角，以及他的標題在ActionBar上
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        Intent accountData = getIntent();
        if(accountData.hasExtra("Extra_id")){
            setTitle("Edit Account");
            Bundle accountBundle= accountData.getExtras();

            binding.editAccount.setText(accountBundle.getString("Extra_account"));
            binding.editInitalBalance.setText(accountBundle.getInt("Extra_initialBalance")+"");
            binding.tvAddEdit.setText("帳戶修改");

        }else{
            setTitle("Add Account");
            binding.tvAddEdit.setText("帳戶新增");
        }


    }


    private void saveAccount(){
        String account = binding.editAccount.getText().toString();
        String StringInitialBalance = binding.editInitalBalance.getText().toString().trim();

        if(account.isEmpty()|| StringInitialBalance.isEmpty()){
            Toast.makeText(this,"請輸入帳戶名稱和初始餘額",Toast.LENGTH_SHORT).show();
            return;
        }
        int initialBalance = Integer.parseInt(StringInitialBalance);

        Intent accountData = new Intent();
        Bundle accountBundle = new Bundle();
        accountBundle.putString("Extra_account",account);
        accountBundle.putInt("Extra_initialBalance",initialBalance);


        // 這裡的 getIntExtra(EXTRA_ID, -1) 表示從 Intent 中獲取名為 EXTRA_ID 的額外數據
        // ，如果找不到這個額外數據，則返回默認值 -1
        int id = getIntent().getIntExtra("Extra_id",-1);
        if(id!=-1){
            accountBundle.putInt("Extra_id",id);
        }
        accountData.putExtras(accountBundle);
        setResult(RESULT_OK,accountData);
        finish();
    }

    // 這個方法在創建選項菜單時被調用。
    // 這個方法中通常使用 MenuInflater 加載菜單資源（在這裡是 main_menu.xml），
    // 這樣就可以在應用程式的操作欄上顯示選項。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_expense_menu,menu);
        return true;
    }

    // 這個方法在用戶選擇選項菜單中的項目時被調用。
    // 如果用戶選擇了 "save_expense" 項目，則會呼叫 noteViewModel.deleteAllNotes()
    // 同時顯示一個簡短的 Toast 消息來通知用戶。
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_expense) {
            saveAccount();

            return true;
        } else if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }


}