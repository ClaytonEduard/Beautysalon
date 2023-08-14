/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.model;

import com.claytoneduard.beautysalon.config.ConfiguracaoFirebase;
import com.claytoneduard.beautysalon.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Agenda {

    private int id;
    private String idFuncionario;
    private String idCliente;
    private String idServico;
    private int hora;
    private boolean disponivel;

    public Agenda() {
    }

    public Agenda(int hora, boolean disponivel) {
        this.hora = hora;
        this.disponivel = disponivel;
    }

    public Agenda(int id, String funcionario, String cliente, String servico, int hora, boolean disponivel) {
        this.id = id;
        this.idFuncionario = funcionario;
        this.idCliente = cliente;
        this.idServico = servico;
        this.hora = hora;
        this.disponivel = disponivel;
    }

    public void salvar(){
        DatabaseReference reference = ConfiguracaoFirebase.getFirebaseDatabase();
        reference.child("agenda")
                .push()
                .setValue(this);
    }






    public boolean isDisponivel() {
        return disponivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdServico() {
        return idServico;
    }

    public void setIdServico(String idServico) {
        this.idServico = idServico;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
}
