package com.example.cbmoney.expense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.cbmoney.R;
import com.example.cbmoney.account.ChooseAccount;
import com.example.cbmoney.databinding.ActivityAddIncomeBinding;

import java.util.Calendar;

public class AddIncome extends AppCompatActivity {
    private ActivityAddIncomeBinding binding;
    private int year,month,day;
    public static final int ADD_ACCOUNT_REQUEST = 1;
    public static final int ADD_CATEGORY_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddIncomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 設定叉叉的圖是在左上角，以及他的標題在ActionBar上
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        Intent intent = getIntent();
        // EdtNotefragement的的日期
        showChoseday(intent);

        if(intent.hasExtra("Extra_id")){
            setTitle("Edit Income");
            Bundle bundle2 = intent.getExtras();
            year = bundle2.getInt("Extra_year");
            month = bundle2.getInt("Extra_month");
            day = bundle2.getInt("Extra_day");
            int income = bundle2.getInt("Extra_income");
            String category = bundle2.getString("Extra_category");
            String account = bundle2.getString("Extra_account");
            String description = bundle2.getString("Extra_description");
            binding.inputIcomeDate.setText( year + "年" + month + "月" + day+"日");
            binding.inputIncome.setText(income+"");
            binding.inputIncomeCategory.setText(category);
            binding.inputIncomeDescription.setText(description);
            binding.inputIncomeAccount.setText(account);
        }else{
            setTitle("Add Income");
        }
        setListner();
        goToChooseAccount();
        goToChooseIncomeCategory();
    }
    private void showChoseday(Intent intent){
        Bundle bundle3 = intent.getExtras();
        year = bundle3.getInt("Extra_year");
        month = bundle3.getInt("Extra_month");
        day = bundle3.getInt("Extra_day");
        String selectedDate = year + "年" + month + "月" + day+"日";
        binding.inputIcomeDate.setText(selectedDate);
    }
    //  監聽日期有沒有被按到
    private void setListner(){
        binding.inputIcomeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(AddIncome.this);
            }
        });
    }

    //  跳出DatePickerDialog
    private void showDatePickerDialog(Context context) {
        // 獲取當前日期
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        // 創建 DatePickerDialog 實例
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectyear, int selectmonth, int selectday) {
                        Log.d("chin","second"+selectmonth);
                        year = selectyear;
                        month = selectmonth+1;
                        day = selectday;
                        // 當用戶設置日期後執行的操作
                        String selectedDate = year + "年" + month + "月" + day+"日";
                        binding.inputIcomeDate.setText(selectedDate);

                    }}, currentYear, currentMonth, currentDay);
        // 顯示 DatePickerDialog
        datePickerDialog.show();
    }
    private void goToChooseAccount(){
        binding.inputIncomeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIncome.this, ChooseAccount.class);
                startActivityForResult(intent, ADD_ACCOUNT_REQUEST);
            }
        });
    }
    private void goToChooseIncomeCategory(){
        binding.inputIncomeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIncome.this, ChooseIncomeCategory.class);
                startActivityForResult(intent, ADD_CATEGORY_REQUEST);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_expense_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_expense) {
            saveIncome();

            return true;
        } else if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void saveIncome(){
        String incomeString = binding.inputIncome.getText().toString().trim();

        String category = binding.inputIncomeCategory.getText().toString();
        String account = binding.inputIncomeAccount.getText().toString();
        String description = binding.inputIncomeDescription.getText().toString();


        if(incomeString.isEmpty()){
            Toast.makeText(this,"請輸入金額",Toast.LENGTH_SHORT).show();
            return;
        }
        int income = Integer.parseInt(incomeString);

        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("Extra_year",year);
        bundle.putInt("Extra_month",month);

        bundle.putInt("Extra_day",day);
        bundle.putInt("Extra_income",income);
        bundle.putString("Extra_category",category);
        bundle.putString("Extra_account",account);
        bundle.putString("Extra_description",description);



        // 這裡的 getIntExtra(EXTRA_ID, -1) 表示從 Intent 中獲取名為 EXTRA_ID 的額外數據
        // ，如果找不到這個額外數據，則返回默認值 -1
        int id = getIntent().getIntExtra("Extra_id",-1);
        if(id!=-1){
            bundle.putInt("Extra_id",id);
        }
        data.putExtras(bundle);

        setResult(RESULT_OK,data);
        finish();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String account =  bundle.getString("Extra_account");
            binding.inputIncomeAccount.setText(account);
            Toast.makeText(this,"你選擇的帳戶:"+account,Toast.LENGTH_SHORT).show();
        }else if(requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            String category =  bundle.getString("Extra_category");
            binding.inputIncomeCategory.setText(category);
            Toast.makeText(this,"你選擇的類別:"+category,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"取消選擇",Toast.LENGTH_SHORT).show();
        }
    }
}