/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;

    // retorna a instancia do FirebaseAth
    public static FirebaseAuth getFirebaseAutenticacao() {

        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    // retorna a instancia do FirebaseDatabase
    public static DatabaseReference getFirebaseDatabase() {
        if (firebase == null) {
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }
}
