/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.model;

public class Servico {
    private int id;
    private String descricao;
    private double preco;
    private int duracaoMaximaHoras;

    public Servico(int id, String descricao, double preco, int duracaoMaximaHoras) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoMaximaHoras = duracaoMaximaHoras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
