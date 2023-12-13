package com.example.cbmoney.expense;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbmoney.databinding.ExpenseItemBinding;
import com.example.cbmoney.model.ExpenseEntity;
import com.example.cbmoney.model.IncomeEntity;
import com.example.cbmoney.model.TransactionEntity;


public class TransactionAdapter extends ListAdapter<TransactionEntity, TransactionAdapter.TransactionHolder> {

    private static OnitemClickListner listner;

    // diffCallback 用於比較兩個項目，確定它們是否相同以及它們的內容是否相同。
    public TransactionAdapter() {
        super(diffCallback);
    }

    // 這是一個 DiffUtil.ItemCallback 的實例，用於告訴 Adapter 如何比較兩個項目
    // areItemsTheSame 方法用於檢查兩個項目是否代表同一個對象
    // areContentsTheSame 方法用於檢查兩個項目的內容是否相同。
    private static final DiffUtil.ItemCallback<TransactionEntity> diffCallback = new DiffUtil.ItemCallback<TransactionEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TransactionEntity oldItem, @NonNull TransactionEntity newItem) {
            return TextUtils.equals(oldItem.getCategory(), newItem.getCategory()) &&
                    TextUtils.equals(oldItem.getDescription(), newItem.getDescription()) &&
                    oldItem.getMoney() == newItem.getMoney() &&
                    TextUtils.equals(oldItem.getAccount(), newItem.getAccount());
        }
    };

    @NonNull
    @Override
    // 當 RecyclerView 需要新的 ViewHolder（用於顯示新的項目）時，調用此方法
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 使用通用的布局，這裡假設為 transaction_item.xml
        ExpenseItemBinding binding = ExpenseItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionHolder(binding, this);
    }

    @Override
    // onBindViewHolder： 這是一個方法，用於將數據綁定到特定位置的 ViewHolder
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        ExpenseItemBinding binding = holder.binding;
        TransactionEntity currentTransaction = getItem(position);

        if (currentTransaction instanceof ExpenseEntity) {
            // 如果是 ExpenseEntity，執行相應的操作
            ExpenseEntity currentExpense = (ExpenseEntity) currentTransaction;
            binding.textViewCategory.setText(currentExpense.getCategory());
            binding.textViewDescription.setText(currentExpense.getDescription());
            binding.textViewMoney.setText(" "+String.valueOf(currentExpense.getMoney())+" ");
            binding.textViewAccount.setText(currentExpense.getAccount());
            // 設置背景顏色為綠色
            binding.textViewMoney.setBackgroundColor(binding.getRoot().getContext().getColor(android.R.color.holo_red_light));
        } else if (currentTransaction instanceof IncomeEntity) {
            // 如果是 IncomeEntity，執行相應的操作
            IncomeEntity currentIncome = (IncomeEntity) currentTransaction;
            binding.textViewCategory.setText(currentIncome.getCategory());
            binding.textViewDescription.setText(currentIncome.getDescription());
            binding.textViewMoney.setText(" "+String.valueOf(currentIncome.getMoney())+" ");
            binding.textViewAccount.setText(currentIncome.getAccount());
            // 設置背景顏色為紅色
            binding.textViewMoney.setBackgroundColor(binding.getRoot().getContext().getColor(android.R.color.holo_green_light));
        }
    }

    // 這個方法的目的是獲取在特定位置（position）上的 ExpenseEntity 對象。
    public TransactionEntity getTransactionAt(int position) {
        return getItem(position);
    }

    // ViewHolder 的目的是保持項目的 View
    static class TransactionHolder extends RecyclerView.ViewHolder {
        private final ExpenseItemBinding binding;
        private final TransactionAdapter adapter;

        public TransactionHolder(ExpenseItemBinding binding, TransactionAdapter adapter) {
            super(binding.getRoot());
            this.binding = binding;
            this.adapter = adapter;

            // 點擊整個項目的操作通常會在 ViewHolder 中進行設置，以便在點擊項目時觸發相應的操作
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(adapter.getTransactionAt(position));
                    }
                }
            });
        }
    }

    public interface OnitemClickListner {
        void onItemClick(TransactionEntity transaction);
    }

    public void setOnclickListner(OnitemClickListner listner) {
        this.listner = listner;
    }

}


