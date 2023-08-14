/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.claytoneduard.beautysalon.R;

import java.util.Calendar;
import java.util.List;

public class CustomDatePickerDialog extends DatePickerDialog {

    private final List<Calendar> holidayDates;
    private List<Calendar> availableDates;

    public CustomDatePickerDialog(@NonNull Context context, OnDateSetListener listener, int year, int month, int day, List<Calendar> holidayDates) {
        super(context, listener, year, month, day);
        this.holidayDates = holidayDates;
        fixDatePickerSpinner(context, year, month, day);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);

        for (Calendar holiday : holidayDates) {
            if (selectedDate.get(Calendar.DAY_OF_YEAR) == holiday.get(Calendar.DAY_OF_YEAR)) {
                view.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.purple_700));
                view.setEnabled(false);
                return;
            }
        }

        if (selectedDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            view.setEnabled(false);
        } else {
            view.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
            view.setEnabled(true);
        }
    }

    private void fixDatePickerSpinner(Context context, int year, int monthOfYear, int dayOfMonth) {
        try {
            java.lang.reflect.Field[] datePickerDialogFields = DatePickerDialog.class.getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(this);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        if ("mDelegate".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object datePickerDelegate = datePickerField.get(datePicker);
                            Class<?> delegateClass = datePickerField.getType();
                            java.lang.reflect.Method getDayOfWeekStart = delegateClass.getDeclaredMethod("getDayOfWeekStart");
                            getDayOfWeekStart.setAccessible(true);
                            int startOfWeek = (int) getDayOfWeekStart.invoke(datePickerDelegate);

                            java.lang.reflect.Method setDayOfWeekStart = delegateClass.getDeclaredMethod("setDayOfWeekStart", int.class);
                            setDayOfWeekStart.setAccessible(true);
                            setDayOfWeekStart.invoke(datePickerDelegate, startOfWeek);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
