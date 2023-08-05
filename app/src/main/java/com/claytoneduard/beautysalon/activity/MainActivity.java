/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.claytoneduard.beautysalon.R;
import com.claytoneduard.beautysalon.activity.CadastroActivity;
import com.claytoneduard.beautysalon.activity.LoginActivity;
import com.claytoneduard.beautysalon.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //ocultando os botoes
        setButtonBackVisible(false);
        setButtonNextVisible(false);
        int cor = R.color.base;
        addSlide(new FragmentSlide.Builder()
                .background(cor)
                .fragment(R.layout.intro_1)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(cor)
                .fragment(R.layout.intro_2)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(cor)
                .fragment(R.layout.intro_3)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(cor)
                .fragment(R.layout.intro_4)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(cor)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(true)
                .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void btEntrar(View view) {
        //chamando a activity entrar
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btCadastrar(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }


    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        // verifica se existe um usuario logado
        if(autenticacao.getCurrentUser()!=null){
            abrirTelaPrincipal();
        }
    }
    // abrir a tela principal
    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, PrincipalActivity.class));
    }

}