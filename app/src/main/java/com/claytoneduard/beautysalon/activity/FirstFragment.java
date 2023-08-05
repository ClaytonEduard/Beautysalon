/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.claytoneduard.beautysalon.R;
import com.claytoneduard.beautysalon.adapter.ReservaAdapter;
import com.claytoneduard.beautysalon.databinding.FragmentFirstBinding;
import com.claytoneduard.beautysalon.model.Reserva;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {


    private FragmentFirstBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     /*   binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}