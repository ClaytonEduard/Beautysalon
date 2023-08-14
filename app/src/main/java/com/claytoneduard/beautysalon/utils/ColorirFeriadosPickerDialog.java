/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

public class ColorirFeriadosPickerDialog extends DatePickerDialog {

    private List<Calendar> holidayDates;

    public ColorirFeriadosPickerDialog(@NonNull Context context, OnDateSetListener listener, int year, int month, int day, List<Calendar> holidayDates) {
        super(context, listener, year, month, day);
        this.holidayDates = holidayDates;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatePicker datePicker = getDatePicker();
        for (Calendar holiday : holidayDates) {
            if (datePicker.getDayOfMonth() == holiday.get(Calendar.DAY_OF_MONTH) &&
                    datePicker.getMonth() == holiday.get(Calendar.MONTH) &&
                    datePicker.getYear() == holiday.get(Calendar.YEAR)) {
                datePicker.updateDate(holiday.get(Calendar.YEAR), holiday.get(Calendar.MONTH), holiday.get(Calendar.DAY_OF_MONTH));
                datePicker.setBackgroundColor(25); // Defina a cor de fundo azul
                datePicker.setEnabled(false); // Desabilitar seleção do feriado
            }
        }
    }
}
