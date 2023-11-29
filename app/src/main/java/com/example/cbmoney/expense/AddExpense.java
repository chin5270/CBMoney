package com.example.cbmoney.expense;

import androidx.annotation.NonNull;
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
import com.example.cbmoney.databinding.ActivityAddExpenseBinding;

import java.util.Calendar;

public class AddExpense extends AppCompatActivity {
    ActivityAddExpenseBinding binding;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 設定叉叉的圖是在左上角，以及他的標題在ActionBar上
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        // 今天的日期
        showToday();

        // intent 中拿到Extra_id ，則將找到的資料綁到介面上，為了修改原本的資料，並且設Title 為 Edit expense
        Intent intent = getIntent();
        if(intent.hasExtra("Extra_id")){
            setTitle("Edit expense");
            Bundle bundle2 = intent.getExtras();
            year = bundle2.getInt("Extra_year");
            month = bundle2.getInt("Extra_month");
            day = bundle2.getInt("Extra_day");
            int expense = bundle2.getInt("Extra_expense");
            Log.d("chin","AddExpense:"+day);
            String category = bundle2.getString("Extra_category");
            String account = bundle2.getString("Extra_account");
            String description = bundle2.getString("Extra_description");
            binding.inputDate.setText( year + "年" + month + "月" + day+"日");
            binding.inputExpense.setText(expense+"");
            binding.inputCategory.setText(category);
            binding.inputDescription.setText(description);
        }else{
            setTitle("Add expense");
        }



        // 選擇的日期(按到才有)
        setListner();

    }


//  每次onCreate都會是今天的日期
    private void showToday(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        String selectedDate = year + "年" + (month+1) + "月" + day+"日";
        binding.inputDate.setText(selectedDate);
    }

//  監聽日期有沒有被按到
    private void setListner(){
        binding.inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(AddExpense.this);
            }
        });
    }

//  跳出DatePickerDialog
    private void showDatePickerDialog(Context context) {
        // 獲取當前日期
        Calendar calendar = Calendar.getInstance();
        int selectyear = calendar.get(Calendar.YEAR);
        int selectmonth = calendar.get(Calendar.MONTH);
        int selectday = calendar.get(Calendar.DAY_OF_MONTH);
        // 創建 DatePickerDialog 實例
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectyear, int selectmonth, int selectday) {
                        year = selectyear;
                        month = selectmonth;
                        day = selectday;
                        // 當用戶設置日期後執行的操作
                        String selectedDate = year + "年" + (month+1) + "月" + day+"日";
                        binding.inputDate.setText(selectedDate);
                    }}, year, month, day);
        // 顯示 DatePickerDialog
        datePickerDialog.show();
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
            saveExpense();

            return true;
        } else if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }


    // 傳遞數據
    private void saveExpense() {
        String expenseString = binding.inputExpense.getText().toString().trim();

        String category = binding.inputCategory.getText().toString();
        String account = binding.inputAccount.getText().toString();
        String description = binding.inputDescription.getText().toString();


        if(expenseString.isEmpty()|| description.trim().isEmpty()){
            Toast.makeText(this,"請輸入金額和備註",Toast.LENGTH_SHORT).show();
            return;
        }
        int expense = Integer.parseInt(expenseString);

        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("Extra_year",year);
        bundle.putInt("Extra_month",month);
        bundle.putInt("Extra_day",day);
        bundle.putInt("Extra_expense",expense);
        bundle.putString("Extra_category",category);
        bundle.putString("Extra_account",account);
        bundle.putString("Extra_description",description);



        // 這裡的 getIntExtra(EXTRA_ID, -1) 表示從 Intent 中獲取名為 EXTRA_ID 的額外數據
        // ，如果找不到這個額外數據，則返回默認值 -1
        int id = getIntent().getIntExtra("Extra_id",-1);
        Log.d("chin",id+"");
        if(id!=-1){
            bundle.putInt("Extra_id",id);
        }
        data.putExtras(bundle);

        setResult(RESULT_OK,data);
        finish();


    }


}
