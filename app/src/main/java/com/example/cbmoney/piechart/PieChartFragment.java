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
        legend.setWordWrapEnabled(true);
        legend.setTextColor(Color.WHITE);
        legend.setDrawInside(false);
        pieChart.setDrawHoleEnabled(false);

        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(80,"數學"));
        entries.add(new PieEntry(90,"英文"));
        entries.add(new PieEntry(10,"日文"));
        entries.add(new PieEntry(100,"德文"));
        entries.add(new PieEntry(60,"法文"));



        PieDataSet pieDataSet = new PieDataSet(entries,"subjects");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(500);
        pieChart.invalidate();

        return  binding.getRoot();


    }
}