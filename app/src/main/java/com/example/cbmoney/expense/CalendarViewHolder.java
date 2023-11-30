package com.example.cbmoney.expense;

import android.view.View;


import androidx.recyclerview.widget.RecyclerView;

import com.example.cbmoney.databinding.CalendarCellBinding;


public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CalendarCellBinding binding;
    private final CalendarAdapter.OnItemListner onItemListner;

    public CalendarViewHolder(CalendarCellBinding binding, CalendarAdapter.OnItemListner onItemListner) {
        super(binding.getRoot());
        this.binding = binding;
        this.onItemListner = onItemListner;
        itemView.setOnClickListener(this);
    }

    public void bind(String dayText) {
        binding.cellDayText.setText(dayText);
        binding.getRoot().setOnClickListener(v ->
                onItemListner.onItemClick(getAdapterPosition(), dayText));
        binding.executePendingBindings();
    }

    @Override
    public void onClick(View v) {
        // 在這裡添加點擊後的操作，如果有的話
    }
}
