package com.example.cbmoney.piechart;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.cbmoney.R;
import com.example.cbmoney.databinding.FragmentAccountBinding;
import com.example.cbmoney.databinding.FragmentPieChartBinding;
import com.example.cbmoney.expense.ExpenseViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

public class PieChartFragment extends Fragment  implements DatePickerDialog.OnDateSetListener {
    private FragmentPieChartBinding binding;
    private ExpenseViewModel expenseViewModel;
    private String[] data ={"飲食","日常開銷","娛樂","醫療保健","其他"} ;
    private CountDownLatch latch;
    private ArrayList<Integer> allCategoryExpenseForMonth = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPieChartBinding.inflate(inflater, container, false);

        // 初始化 expenseViewModel
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);


        // 設置時間選擇器
        binding.datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pd = new MonthYearPickerDialog();
                pd.setListener(PieChartFragment.this);
                pd.show(requireActivity().getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        binding.datePickerButton.setText("  "+year+"年 "+month+"月"+"  ▾  ");


        updatePieChart(year,month);

        return  binding.getRoot();


    }


    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        binding.datePickerButton.setText("  " + year + "年 " + month + "月" + "  ▾  ");
        updatePieChart(year,month);
    }

    private void updatePieChart(int year,int month){
        allCategoryExpenseForMonth.clear();
        categories.clear();
        // 初始化 CountDownLatch，設置 count 為 data.length
        latch = new CountDownLatch(data.length);

        // 在 onCreateView 或其他地方註冊 Observer
        for (int i = 0; i < data.length; i++) {
            final int finalI = i;
            expenseViewModel.setCategoryExpenseForMonth(year, month, data[i]);

            expenseViewModel.getCategoryExpenseForMonth().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer CategoryExpense) {
                    if (CategoryExpense == null) {
                        allCategoryExpenseForMonth.add(0);
                        categories.add(data[finalI]);

                    } else {
                        allCategoryExpenseForMonth.add(CategoryExpense);
                        categories.add(data[finalI]);
                    }

                    // 每次 onChanged 被呼叫時，減少 CountDownLatch 的 count
                    latch.countDown();

                    // 當所有觀察者都被呼叫後進行進一步的邏輯
                    if (latch.getCount() == 0) {
                        setPieChart();
                    }
                }
            });
        }
    }

    private void setPieChart() {
        PieChart pieChart = binding.chart;
        Legend legend = pieChart.getLegend();
        legend.setTextColor(Color.WHITE);

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int j = 0; j < allCategoryExpenseForMonth.size(); j++) {
            if (allCategoryExpenseForMonth.get(j) != 0) {
                entries.add(new PieEntry(allCategoryExpenseForMonth.get(j), categories.get(j)));
            }
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieDataSet.setDrawValues(false);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(500);
        pieChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LiveData<Integer> categoryExpenseLiveData = expenseViewModel.getCategoryExpenseForMonth();
        if (categoryExpenseLiveData != null) {
            // 移除 Observer，避免內存泄漏
            categoryExpenseLiveData.removeObservers(getViewLifecycleOwner());
        }
    }



}