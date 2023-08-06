/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.claytoneduard.beautysalon.R;

public class CancelarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar);
        getSupportActionBar().setTitle("Cancelar");
    }
}