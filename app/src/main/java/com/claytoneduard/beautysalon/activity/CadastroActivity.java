package com.claytoneduard.beautysalon.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.claytoneduard.beautysalon.R;
import com.claytoneduard.beautysalon.config.ConfiguracaoFirebase;
import com.claytoneduard.beautysalon.enun.TipoUsuario;
import com.claytoneduard.beautysalon.helper.Base64Custom;
import com.claytoneduard.beautysalon.model.Usuario;
import com.claytoneduard.beautysalon.utils.Mask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Calendar;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha, campoTelefone, campoDataNascimento;
    private Button botaoCadastrar;
    private DatePickerDialog datePickerDialog;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmailCadastro);
        campoSenha = findViewById(R.id.editSenhaCadastro);
        campoTelefone = findViewById(R.id.editTelefone);
        campoDataNascimento = findViewById(R.id.editDataNascimento);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        // informar a maskara no telefone
        campoTelefone.addTextChangedListener(Mask.insert("(##)#####-####", campoTelefone));

        campoDataNascimento.setInputType(InputType.TYPE_NULL);
        campoDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // inicio do picker
                datePickerDialog = new DatePickerDialog(CadastroActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        campoDataNascimento.setText(dia + "/" + (mes + 1) + "/" + ano);
                    }
                }, year, month, day);
                // datePickerDialog.setTitle(meses.toString());
                datePickerDialog.show();
            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNome = campoNome.getText().toString();
                String textoTelefone = campoTelefone.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();
                String textoDataNascimento = campoDataNascimento.getText().toString();

                // verificação se houve digitação
                if (!textoNome.isEmpty()) {
                    if (!textoTelefone.isEmpty()) {
                        if (!textoEmail.isEmpty()) {
                            if (!textoSenha.isEmpty()) {
                                usuario = new Usuario();
                                usuario.setNome(textoNome);
                                usuario.setTelefone(textoTelefone);
                                usuario.setEmail(textoEmail);
                                usuario.setSenha(textoSenha);
                                if (textoDataNascimento.isEmpty() || textoDataNascimento == null) {
                                    usuario.setDataNascimento("");
                                } else {
                                    usuario.setDataNascimento(textoDataNascimento);
                                }
                                cadastrarUsuario();
                            } else {
                                Toast.makeText(CadastroActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CadastroActivity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroActivity.this, "Preencha o telefone", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroActivity.this, "Preencha o nome", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                            usuario.setIdUsuario(idUsuario);
                            usuario.setTipoUsuario(TipoUsuario.CLIENTE);
                            usuario.salvar();
                            finish();
                        } else {
                            String excecao = "";
                            // lancar excecao
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Por favor, digite um e-mail válido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao = "Esta conta já foi cadastrada!";
                            } catch (Exception e) {
                                excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}