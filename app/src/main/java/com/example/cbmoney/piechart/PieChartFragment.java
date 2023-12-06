package com.example.cbmoney.piechart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbmoney.R;
import com.example.cbmoney.databinding.FragmentAccountBinding;
import com.example.cbmoney.databinding.FragmentPieChartBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartFragment extends Fragment {
    private FragmentPieChartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPieChartBinding.inflate(inflater, container, false);

        PieChart pieChart = binding.chart;
        Legend legend = pieChart.getLegend();
       // legend.setTextSize(12f); // 設置文字大小
        legend.setTextColor(Color.WHITE); // 設置文字顏色
        pieChart.setDrawHoleEnabled(false); // 啟用中空區域

        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(80,"Maths"));
        entries.add(new PieEntry(90,"Englsh"));
        entries.add(new PieEntry(10,"Science"));
        entries.add(new PieEntry(100,"Chinese"));

        PieDataSet pieDataSet = new PieDataSet(entries,"subjects");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();

        return  binding.getRoot();

    }
}