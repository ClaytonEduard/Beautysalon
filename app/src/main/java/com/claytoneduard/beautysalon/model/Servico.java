/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.model;

import com.claytoneduard.beautysalon.config.ConfiguracaoFirebase;
import com.claytoneduard.beautysalon.helper.Base64Custom;
import com.claytoneduard.beautysalon.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Servico {
    private String id;
    private String descricao;
    private double preco;
    private int duracaoMaximaHoras;

    public Servico() {
    }

    // metodo salvar
    public void salvar(){
        //recuperar o email do user logado
        FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //captura o user logado
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        DatabaseReference reference = ConfiguracaoFirebase.getFirebaseDatabase();
        reference.child("servico")
                //.child(idUsuario)
                .push()
                .setValue(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getDuracaoMaximaHoras() {
        return duracaoMaximaHoras;
    }

    public void setDuracaoMaximaHoras(int duracaoMaximaHoras) {
        this.duracaoMaximaHoras = duracaoMaximaHoras;
    }
}
