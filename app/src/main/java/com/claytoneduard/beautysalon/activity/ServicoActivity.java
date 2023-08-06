/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.claytoneduard.beautysalon.R;
import com.claytoneduard.beautysalon.config.ConfiguracaoFirebase;
import com.claytoneduard.beautysalon.model.Servico;
import com.claytoneduard.beautysalon.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ServicoActivity extends AppCompatActivity {

    private EditText campoDescricao, campoValor, campoDuracaoMaxHora;
    private Button buttonCadastrar;
    private Usuario usuario;
    private Servico servico;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico);
        getSupportActionBar().setTitle("Cadastrar Serviço");

        campoDescricao = findViewById(R.id.editDescricaoServico);
        campoValor = findViewById(R.id.editValorServico);
        campoDuracaoMaxHora = findViewById(R.id.editDuracaoMaximaServico);
        buttonCadastrar = findViewById(R.id.buttonCadastrarServico);

    }

    public void cadastrarServico(View view) {

        servico = new Servico();
        servico.setDescricao(campoDescricao.getText().toString());
        servico.setDuracaoMaximaHoras(Integer.parseInt(campoDuracaoMaxHora.getText().toString()));
        servico.setPreco(Double.parseDouble(campoValor.getText().toString()));
        servico.salvar();

    }
}