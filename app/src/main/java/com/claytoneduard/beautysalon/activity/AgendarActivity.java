/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.claytoneduard.beautysalon.R;
import com.claytoneduard.beautysalon.config.ConfiguracaoFirebase;
import com.claytoneduard.beautysalon.enun.TipoUsuario;
import com.claytoneduard.beautysalon.model.Servico;
import com.claytoneduard.beautysalon.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AgendarActivity extends AppCompatActivity {

    private Spinner spinnerFuncionario, spinnerServico, spinnerHorario;
    private Button btnSalvarReserva;
    private TextView dataSelecionada, horaSelecionada;
    private DatePickerDialog datePickerDialog;
    private List<Usuario> funcionarios; // Suponha que você tenha uma lista de funcionários
    private List<Usuario> clientes; // Suponha que você tenha uma lista de clientes
    private List<Servico> servicos;
    private List<Integer> horasList;

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);
        getSupportActionBar().setTitle("Agendar");

        btnSalvarReserva = findViewById(R.id.btnSalvarReserva);
        dataSelecionada = findViewById(R.id.textDataSelecionada);
        horaSelecionada = findViewById(R.id.textHoraSelecionada);
        spinnerFuncionario = findViewById(R.id.spinnerFuncionario);
        spinnerServico = findViewById(R.id.spinnerServico);
        spinnerHorario = findViewById(R.id.spinnerHorario);

        carregarSpinners();
        carregarSpinnerHorario();

        dataSelecionada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int hora = calendar.get(Calendar.HOUR);
                int minutes = calendar.get(Calendar.MINUTE);
                datePickerDialog = new DatePickerDialog(AgendarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        dataSelecionada.setText(dia + "/" + (mes + 1) + "/" + ano);
                    }
                }, year, month, day);

                // selecionar datas ate a data de hoje
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

    }

    public void carregarSpinnerHorario() {
        final Calendar calendar = Calendar.getInstance();

        int horaAtual = calendar.get(Calendar.HOUR_OF_DAY);
        horasList = new ArrayList<>();
        horasList.clear();
        for (int i = 7; i <= 20; i++) {
            if ( i >horaAtual) {
               horasList.add(i);
            }
        }
        Toast.makeText(this, "Hora Atual: " + horaAtual, Toast.LENGTH_LONG).show();
        ArrayAdapter<Integer> horarios = new ArrayAdapter<>(AgendarActivity.this,
                android.R.layout.simple_spinner_dropdown_item, horasList);
        spinnerHorario.setAdapter(horarios);
    }


    // carregar spinner
    public void carregarSpinners() {
        // Configurar os adaptadores para os spinners
        funcionarios = new ArrayList<>();
        DatabaseReference userRef = firebaseRef.child("usuarios");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                funcionarios.clear();
                for (DataSnapshot funSnap : snapshot.getChildren()) {
                    String nome = funSnap.child("nome").getValue(String.class);
                    String tipo = funSnap.child("tipoUsuario").getValue(String.class);
                    String id = funSnap.child("id").getValue(String.class);
                    TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipo);
                    Usuario u = new Usuario(id, nome, tipoUsuario);
                    if (tipo.equals(TipoUsuario.FUNCIONARIO.toString())) {
                        funcionarios.add(u);
                    }
                }
                ArrayAdapter<Usuario> funcionarioAdapter = new ArrayAdapter<>(AgendarActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, funcionarios);
                spinnerFuncionario.setAdapter(funcionarioAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        servicos = new ArrayList<>();
        DatabaseReference servicoRef = firebaseRef.child("servico");
        servicoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                servicos.clear();
                for (DataSnapshot servSnap : snapshot.getChildren()) {
                    String id = servSnap.child("id").getValue(String.class);
                    String descricao = servSnap.child("descricao").getValue(String.class);
                    int duracao = servSnap.child("duracaoMaximaHoras").getValue(Integer.class);
                    double valor = servSnap.child("preco").getValue(Double.class);
                    Servico s = new Servico(id, descricao, valor, duracao);
                    servicos.add(s);
                }
                ArrayAdapter<Servico> servicoArrayAdapter = new ArrayAdapter<>(AgendarActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, servicos);
                spinnerServico.setAdapter(servicoArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}