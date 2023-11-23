package com.example.cbmoney;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbmoney.databinding.ExpenseItemBinding;


public class ExpenseAdapter extends ListAdapter<ExpenseEntity,ExpenseAdapter.ExpenseHolder> {

    private static OnitemClickListner listner;

    // diffCallback 用於比較兩個項目，確定它們是否相同以及它們的內容是否相同。
    public ExpenseAdapter() {
        super(diffCallback);
    }

    // 這是一個 DiffUtil.ItemCallback 的實例，用於告訴 Adapter 如何比較兩個項目
    // areItemsTheSame 方法用於檢查兩個項目是否代表同一個對象
    // areContentsTheSame 方法用於檢查兩個項目的內容是否相同。
    private static final DiffUtil.ItemCallback<ExpenseEntity> diffCallback = new DiffUtil.ItemCallback<ExpenseEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull ExpenseEntity oldItem, @NonNull ExpenseEntity newItem) {
            return oldItem.getId()== newItem.getId();
        }


        @Override
        public boolean areContentsTheSame(@NonNull ExpenseEntity oldItem, @NonNull ExpenseEntity newItem) {
            return TextUtils.equals(oldItem.getCategory(), newItem.getCategory()) &&
                    TextUtils.equals(oldItem.getDescription(), newItem.getDescription()) &&
                    oldItem.getExpense() == newItem.getExpense() &&
                    TextUtils.equals(oldItem.getAccount(), newItem.getAccount());
        }

    };
    @NonNull
    @Override
    // 當 RecyclerView 需要新的 ViewHolder（用於顯示新的項目）時，調用此方法
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExpenseItemBinding binding = ExpenseItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ExpenseHolder(binding, this);
    }

    @Override
    // onBindViewHolder： 這是一個方法，用於將數據綁定到特定位置的 ViewHolder
    // 透過 Expenses.get(position) 獲取了在 RecyclerView 中特定位置的 Note 對象
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        ExpenseItemBinding binding = holder.binding;
        ExpenseEntity currentExpense = getItem(position);
        binding.textViewCategory.setText(currentExpense.getCategory());
        binding.textViewDescription.setText(currentExpense.getDescription());
        binding.textViewMoney.setText(String.valueOf(currentExpense.getExpense()));
        binding.textViewAccount.setText(currentExpense.getAccount());
    }
    // 這個方法的目的是獲取在特定位置（position）上的 ExpenseEntity 對象。
    // 有一個存儲 ExpenseEntity 對象的列表（Expenses）。這個方法允許通過位置索引，直接獲取在該位置上的 ExpenseEntity 對象
    public ExpenseEntity getExpenseAt(int position){

        return getItem(position);
    }

    // ViewHolder 的目的是保持項目的 View
    static class ExpenseHolder extends RecyclerView.ViewHolder {
        private final ExpenseItemBinding binding;
        private final ExpenseAdapter adapter;


        public ExpenseHolder(ExpenseItemBinding binding, ExpenseAdapter adapter) {
            super(binding.getRoot());
            this.binding = binding;
            this.adapter = adapter;

            // itemView 是 RecyclerView.ViewHolder 的一個屬性，它代表 ViewHolder 所持有的整個項目的 View
            // 點擊整個項目的操作通常會在 ViewHolder 中進行設置，以便在點擊項目時觸發相應的操作
            // 通過 getAdapterPosition() 方法獲取點擊的項目在 Adapter 中的位置
            // 如果條件成立，則通知監聽器（listner）有項目被點擊，同時傳遞相應位置的項目數據
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(adapter.getItem(position));
                    }
                }
            });
        }
    }
    public interface OnitemClickListner{
        void onItemClick(ExpenseEntity expense);
    }


    public void setOnclickListner(OnitemClickListner listner){

        this.listner = listner;
    }

}
