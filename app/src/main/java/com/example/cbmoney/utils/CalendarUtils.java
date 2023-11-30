package com.example.cbmoney.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarUtils {
    public static ArrayList<String> daysInMonthArray(Calendar date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        Calendar firstOfMonth = (Calendar) date.clone();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);

        int daysInMonth = firstOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = firstOfMonth.get(Calendar.DAY_OF_WEEK);

        // 需要修正 dayOfWeek 的計算方式
        int adjustedDayOfWeek = (dayOfWeek - firstOfMonth.getFirstDayOfWeek() + 7) % 7;

        for (int i = 1; i <= 42; i++) {
            if (i <= adjustedDayOfWeek || i > daysInMonth + adjustedDayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - adjustedDayOfWeek));
            }
        }
        return daysInMonthArray;
    }
    public static String monthYearFromDate(Calendar date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        return formatter.format(date.getTime());
    }
    public static int getYearFromDate(Calendar date) {
        return date.get(Calendar.YEAR);
    }

    public static int getMonthFromDate(Calendar date) {
        return date.get(Calendar.MONTH) + 1; // 月份是從 0 開始的，所以要加 1
    }

}
