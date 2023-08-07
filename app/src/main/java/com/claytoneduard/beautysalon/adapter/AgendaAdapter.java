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
import com.claytoneduard.beautysalon.model.Agenda;

import java.util.List;

public class AgendaAdapter extends BaseAdapter {
    private Context context;
    private List<Agenda> listaAgenda;

    public AgendaAdapter(Context context, List<Agenda> listaAgenda) {
        this.context = context;
        this.listaAgenda = listaAgenda;
    }

    @Override
    public int getCount() {
        return listaAgenda.size();
    }

    @Override
    public Object getItem(int position) {
        return listaAgenda.get(position);
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

        Agenda agenda = listaAgenda.get(position);

        TextView textViewHora = view.findViewById(R.id.textViewHora);
        textViewHora.setText(String.valueOf(agenda.getHora()));

        if (agenda.isDisponivel()) {
            textViewHora.setTextColor(context.getResources().getColor(android.R.color.black));
        } else {
            textViewHora.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
        }

        return view;
    }

}
