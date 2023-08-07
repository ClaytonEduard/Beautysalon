/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.model;

public class Agenda {

    private int id;
    private Usuario funcionario;
    private Usuario cliente;
    private Servico servico;
    private int hora;
    private boolean disponivel;

    public Agenda(int hora, boolean disponivel) {
        this.hora = hora;
        this.disponivel = disponivel;
    }

    public Agenda(int id, Usuario funcionario, Usuario cliente, Servico servico, int hora, boolean disponivel) {
        this.id = id;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.servico = servico;
        this.hora = hora;
        this.disponivel = disponivel;
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

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
}
