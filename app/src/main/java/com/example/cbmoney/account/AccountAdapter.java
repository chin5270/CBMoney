package com.example.cbmoney.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbmoney.databinding.AccountItemBinding;
import com.example.cbmoney.databinding.ExpenseItemBinding;
import com.example.cbmoney.expense.ExpenseAdapter;
import com.example.cbmoney.model.AccountEntity;
import com.example.cbmoney.model.ExpenseEntity;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {
    private List<AccountEntity> accounts = new ArrayList<>();
    private OnIttemClickListner listner;

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AccountItemBinding binding = AccountItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new AccountHolder(binding,this);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        AccountItemBinding binding = holder.binding;
        AccountEntity currentAccount = accounts.get(position);
        binding.tvAccount.setText(currentAccount.getAccountName());

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }
    public AccountEntity getAcountAt(int position){
        return accounts.get(position);

    }

    public void setAccounts(List<AccountEntity> accounts){
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    class AccountHolder extends RecyclerView.ViewHolder{
        private final AccountItemBinding binding;
        private final AccountAdapter accountAdapter;
        public AccountHolder(AccountItemBinding binding, AccountAdapter accountAdapter) {
            super(binding.getRoot());
            this.binding = binding;
            this.accountAdapter = accountAdapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listner!=null && position!=RecyclerView.NO_POSITION){
                        listner.onItemClick(accounts.get(position));
                    }

                }
            });
        }

    }

    public interface OnIttemClickListner{
        void onItemClick(AccountEntity account);
    }
    public void setOnItemClickListener(OnIttemClickListner listener){
        this.listner = listener;
    }
}
