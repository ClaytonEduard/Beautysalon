/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.claytoneduard.beautysalon.R;
import com.claytoneduard.beautysalon.model.Reserva;

import java.util.List;

public class ReservaAdapter extends BaseAdapter {
    private Context context;
    private List<Reserva> listaReservas;

    public ReservaAdapter(Context context, List<Reserva> listaReservas) {
        this.context = context;
        this.listaReservas = listaReservas;
    }

    @Override
    public int getCount() {
        return listaReservas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaReservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_item_reserva, null);
        }

        Reserva reserva = listaReservas.get(position);

        TextView textViewHora = view.findViewById(R.id.textViewHora);
        textViewHora.setText(String.valueOf(reserva.getHora()));

        if (reserva.isDisponivel()) {
            textViewHora.setTextColor(context.getResources().getColor(android.R.color.black));
        } else {
            textViewHora.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
        }

        return view;
    }

}
