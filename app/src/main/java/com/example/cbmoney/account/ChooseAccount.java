package com.example.cbmoney.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbmoney.R;
import com.example.cbmoney.databinding.ActivityAddExpenseBinding;
import com.example.cbmoney.databinding.ActivityChooseAccountBinding;
import com.example.cbmoney.model.AccountEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ChooseAccount extends AppCompatActivity {

    ActivityChooseAccountBinding binding;
    private AccountViewModel accountViewModel;
    private ArrayAdapter adapter; // 統一項目使用的資料

    private String[] data ;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setTitle("Choose Account");
        account = "";

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        LiveData<List<AccountEntity>> allAccounts = accountViewModel.getAllAccounts();

        allAccounts.observe(this, new Observer<List<AccountEntity>>() {
            @Override
            public void onChanged(List<AccountEntity> accountList) {

                if (accountList != null) {
                    data = new String[accountList.size()];

                    for (int i = 0; i < accountList.size(); i++) {
                        data[i] = accountList.get(i).getAccountName();
                    }
                    setAdapter();
                }
            }
        });



        setListners();


    }


    // 按下item 背景顏色改變，並且將account儲存新的名稱
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 將點擊的項目背景色設置為你想要的顏色
            view.setBackgroundColor(Color.GRAY);

            // 將其他項目背景色設置為透明
            for (int i = 0; i < parent.getChildCount(); i++) {
                View childView = parent.getChildAt(i);
                if (i != position) {
                    childView.setBackgroundColor(Color.TRANSPARENT);
                }
            }

            account = ((TextView)view).getText().toString();

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("Extra_account",account);
            intent.putExtras(bundle);

            setResult(RESULT_OK,intent);
            finish();


        }
    };

    private void setListners(){
        binding.listview.setOnItemClickListener(listener);
    }

    // 設定背景
    // 顏色、字體，以及分隔線的顏色
    private void setAdapter(){
     //   adapter = new ArrayAdapter(ChooseAccount.this, android.R.layout.simple_list_item_1,data);
        // 在這裡設置初始的字體顏色為白色
        adapter = new ArrayAdapter<String>(ChooseAccount.this, android.R.layout.simple_list_item_1, data) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // 在這裡設置初始的字體顏色為白色
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);

                return view;
            }
        };
        binding.listview.setDivider(new ColorDrawable(Color.GRAY));
        binding.listview.setDividerHeight(1); // 設置分隔線的高度
        binding.listview.setAdapter(adapter);
    }



}