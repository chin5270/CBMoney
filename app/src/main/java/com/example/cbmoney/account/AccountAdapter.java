package com.example.cbmoney.account;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cbmoney.databinding.AccountItemBinding;
import com.example.cbmoney.model.AccountEntity;
import com.example.cbmoney.model.ExpenseEntity;
import com.example.cbmoney.model.IncomeEntity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {
    private List<AccountEntity> accounts = new ArrayList<>();
    private List<IncomeEntity> incomes = new ArrayList<>();
    private List<ExpenseEntity> expenses = new ArrayList<>();
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
        int accountBalance = calculateAccountBalance(currentAccount, incomes, expenses);

        // 使用 NumberFormat 來格式化帳戶餘額
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedBalance = "$ " + numberFormat.format(accountBalance);

        binding.tvAccount.setText(currentAccount.getAccountName());
        binding.tvMoney.setText(formattedBalance);
        if(accountBalance<0){
            binding.tvMoney.setTextColor(binding.getRoot().getContext().getColor(android.R.color.holo_red_light));

        }

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
    public void setIncomes(List<IncomeEntity> incomes) {
        this.incomes = incomes;
        notifyDataSetChanged();
    }
    public void setExpenses(List<ExpenseEntity> expenses) {
        this.expenses = expenses;
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

    // 計算帳戶餘額的方法
    private int calculateAccountBalance(AccountEntity account, List<IncomeEntity> incomes, List<ExpenseEntity> expenses) {
        // 實現計算邏輯，這裡使用你的邏輯來計算帳戶餘額
        int totalIncome = calculateTotalIncome(account, incomes);
        int totalExpense = calculateTotalExpense(account, expenses);
        return account.getInitialBalance() + totalIncome - totalExpense;
    }

    // 計算特定帳戶的總收入
    private int calculateTotalIncome(AccountEntity account, List<IncomeEntity> incomes) {
        int totalIncome = 0;
        for (IncomeEntity income : incomes) {
            if (income.getAccount().equals(account.getAccountName())) {
                totalIncome += income.getMoney();
            }
        }
        return totalIncome;
    }

    // 計算特定帳戶的總支出
    private int calculateTotalExpense(AccountEntity account, List<ExpenseEntity> expenses) {
        int totalExpense = 0;
        for (ExpenseEntity expense : expenses) {
            if (expense.getAccount().equals(account.getAccountName())) {
                totalExpense += expense.getMoney();
            }
        }
        return totalExpense;
    }

    public interface OnIttemClickListner{
        void onItemClick(AccountEntity account);
    }
    public void setOnItemClickListener(OnIttemClickListner listener){
        this.listner = listener;
    }
}
