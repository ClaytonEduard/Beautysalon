package com.claytoneduard.beautysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.claytoneduard.beautysalon.activity.CadastroActivity;
import com.claytoneduard.beautysalon.activity.LoginActivity;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

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

    public void btEntrar(View view) {
        //chamando a activity entrar
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btCadastrar(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

}