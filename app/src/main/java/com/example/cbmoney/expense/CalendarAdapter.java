package com.example.cbmoney.expense;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbmoney.R;
import com.example.cbmoney.databinding.CalendarCellBinding;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final OnItemListner onItemListner;
    private int selectedItem = -1;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListner onItemListner) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListner = onItemListner;

        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        setSelectedItem(daysOfMonth.indexOf(String.valueOf(currentDay)));
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CalendarCellBinding binding = CalendarCellBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        ViewGroup.LayoutParams layoutParams = binding.getRoot().getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.2);
        return new CalendarViewHolder(binding, onItemListner);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.bind(daysOfMonth.get(position));
        Log.d("chin","selectedItem="+selectedItem);
        if (position == selectedItem) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(R.color.bright_blue));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(android.R.color.transparent));
        }

    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }

    public void clearSelectedItem() {
        selectedItem = -1;
        notifyDataSetChanged();
    }

    public interface OnItemListner {
        void onItemClick(int position, String dayText);
    }
}

