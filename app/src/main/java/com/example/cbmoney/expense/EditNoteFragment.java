package com.example.cbmoney.expense;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


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


import com.example.cbmoney.databinding.FragmentEditNoteBinding;
import com.example.cbmoney.model.ExpenseEntity;

import java.util.List;


public class EditNoteFragment extends Fragment {
    private ExpenseViewModel expenseViewModel;
    private FragmentEditNoteBinding binding;
    public static final int ADD_EXPENSE_REQUEST = 1;
    public static final int EDIT_EXPENSE_REQUEST = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false);


        // 設置 RecyclerView 的佈局管理器。這裡使用了 LinearLayoutManager，它是一種將項目垂直或水平排列的佈局管理器
        // setHasFixedSize 可以優化 RecyclerView 的性能。當你知道內容不會改變 RecyclerView 的大小時，可以設置為 true
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        // 將 ExpenseAdapter 設置為 RecyclerView 的適配器。這將負責管理數據和提供數據項的視圖
        final ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);


        // 透過 ViewModelProvider 的 get 方法，你可以獲取到與當前上下文相關聯的 ExpenseViewModel 實例。
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.setAllExpensesForDay(2023,11,23);
        expenseViewModel.setTotalExpenseForDay(2023,11,23);
        expenseViewModel.getAllExpensesForDay().observe(getViewLifecycleOwner(), new Observer<List<ExpenseEntity>>() {
            // 當 LiveData 中的數據發生變化時，觀察者內的 onChanged 方法將被呼叫，然後ui介面更新
            @Override
            public void onChanged(List<ExpenseEntity> expenses) {
                // submitList()這個方法是 ListAdapter 提供的一個功能，用於提交新的數據列表（expenses）並觸發差異更新
                adapter.submitList(expenses);

            }
        });

        expenseViewModel.getTotalExpenseForDay().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            // 當 LiveData 中的數據發生變化時，觀察者內的 onChanged 方法將被呼叫，然後ui介面更新
            @Override
            public void onChanged(Integer totalExpenseForDay) {
                binding.textViewTotalDay.setText("支出："+totalExpenseForDay);


            }
        });




        swipeExpenseToDelete(adapter,recyclerView);
        goToAddExpense();
        updateExpense(adapter);




        return binding.getRoot();
    }





    private void swipeExpenseToDelete(ExpenseAdapter adapter, RecyclerView recyclerView){
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

            // ExpenseAdapter 中，getExpenseAt 實際上是調用了 getItem(position) 方法，這是 ListAdapter 提供的方法。
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                expenseViewModel.delete(adapter.getExpenseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(),"expense deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

//   按下加號，頁面跳到AddExpense
    private void goToAddExpense(){
        //       startActivityForResult 是一種啟動另一個 Activity 並期望在該 Activity 完成後收到結果的方法。
//       另一個Activity寫下RESULT_OK 表示操作成功，你也可以使用 RESULT_CANCELED 表示操作取消。
        binding.buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddExpense.class);
                startActivityForResult(intent,ADD_EXPENSE_REQUEST);
            }
        });
    }

    // 更改原本的某筆資料
    public void updateExpense(ExpenseAdapter adapter){
        // 當項目被點擊時，創建一個新的 Intent，將相應的 ExpenseEntity 對象的信息放入 Intent 中，
        // 然後啟動 AddEditNoteActivity 以編輯該筆資料，
        // 需要確保在 ExpenseAdapter 中添加相應的方法
        adapter.setOnclickListner(new ExpenseAdapter.OnitemClickListner() {
            @Override
            public void onItemClick(ExpenseEntity expense) {
                Intent intent = new Intent(getActivity(), AddExpense.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("Extra_id",expense.getId());
                bundle2.putInt("Extra_year",expense.getYear());
                bundle2.putInt("Extra_month",expense.getMonth());
                bundle2.putInt("Extra_day",expense.getDay());
                bundle2.putInt("Extra_expense",expense.getExpense());
                bundle2.putString("Extra_category",expense.getCategory());
                bundle2.putString("Extra_account",expense.getAccount());
                bundle2.putString("Extra_description",expense.getDescription());
                intent.putExtras(bundle2);

                startActivityForResult(intent,EDIT_EXPENSE_REQUEST);
            }
        });
    }

    // 從AddExpense 拿資料，並將資料儲存
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EXPENSE_REQUEST && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            int year = bundle.getInt("Extra_year");
            int month = bundle.getInt("Extra_month");
            int day = bundle.getInt("Extra_day");
            int expense = bundle.getInt("Extra_expense");
            String category = bundle.getString("Extra_category");
            String account = bundle.getString("Extra_account");
            String description = bundle.getString("Extra_description");
            expenseViewModel.insert(new ExpenseEntity(category,description,expense,account,year,month+1,day));
            Toast.makeText(getContext(),"expense saved",Toast.LENGTH_SHORT).show();

        }else if(requestCode == EDIT_EXPENSE_REQUEST && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            int id = bundle.getInt("Extra_id",-1);
            Log.d("chin",id+":editfgmt");
            if(id==-1){
                Toast.makeText(getContext(),"expense cannot be updated",Toast.LENGTH_SHORT).show();
                return;
            }
            int year = bundle.getInt("Extra_year");
            int month = bundle.getInt("Extra_month");
            int day = bundle.getInt("Extra_day");
            int expense = bundle.getInt("Extra_expense");
            String category = bundle.getString("Extra_category");
            String account = bundle.getString("Extra_account");
            String description = bundle.getString("Extra_description");
            ExpenseEntity expense1 = new ExpenseEntity(category,description,expense,account,year,month+1,day);
            expense1.setId(id); // etId 的目的是將已存在的 ExpenseEntity 對象的 ID 設置為指定的值。這是因為在更新現有的數據時，Room Database 通常使用 ID 來識別特定的數據項。
            expenseViewModel.update(expense1);
            Toast.makeText(getContext(),"expense updated",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getContext(),"expense not updated",Toast.LENGTH_SHORT).show();
        }
    }


}
