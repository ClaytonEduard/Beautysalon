/*
 * Copyright (c) 2023
 * Created by Clayton Eduard
 * E-mail : clayton_eduard@hotmail.com
 */

package com.claytoneduard.beautysalon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.claytoneduard.beautysalon.R;
import com.claytoneduard.beautysalon.config.ConfiguracaoFirebase;
import com.claytoneduard.beautysalon.enun.TipoUsuario;
import com.claytoneduard.beautysalon.model.Agenda;
import com.claytoneduard.beautysalon.model.Servico;
import com.claytoneduard.beautysalon.model.Usuario;
import com.claytoneduard.beautysalon.utils.ColorirFeriadosPickerDialog;
import com.claytoneduard.beautysalon.utils.CustomDatePickerDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AgendarActivity extends AppCompatActivity {

    private Spinner spinnerFuncionario, spinnerServico, spinnerHorario;
    private Button btnSalvarReserva;
    private TextView dataSelecionada, horaSelecionada;
    private DatePickerDialog datePickerDialog;
    private CustomDatePickerDialog customDatePickerDialog;
    private ColorirFeriadosPickerDialog colorirFeriadosPickerDialog;
    private List<Usuario> funcionarios; // Suponha que você tenha uma lista de funcionários
    private List<Usuario> clientes; // Suponha que você tenha uma lista de clientes
    private List<Servico> servicos;
    private List<Integer> horasList;
    private Agenda agenda;
    private Usuario usuario;
    public static String urlApi = "https://brasilapi.com.br/api/feriados/v1/";
    private List<JSONObject> listFeriadosNacionais;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Thread t;
    private List<String> feriados = new ArrayList<>();
    private List<Calendar> feriadosNacionais = new ArrayList<>();

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
        listarFeriadosNacionais();

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
                        receberData(String.valueOf(dia));
                    }
                }, year, month, day);

                //Datas Disponiveis
                List<Calendar> datasDisponiveis = getDatasDisponiveis();// datas disponiveis menos domingos
                List<Calendar> datasFinais = getListarDatasFeriados(datasDisponiveis,converterListStrigFeriadoToCalendarList(feriadosNacionais(feriados)));

                //getListarDatasFeriados();

                // selecionar datas ate a data de hoje
                datePickerDialog.getDatePicker().setMinDate(datasFinais.get(0).getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(datasFinais.get(datasFinais.size()-1).getTimeInMillis());
                datePickerDialog.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    //metodo salvar reserva
    public void salvarReserva() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        agenda = new Agenda();
        agenda.setIdCliente(autenticacao.getCurrentUser().getEmail());
        agenda.setIdFuncionario(spinnerFuncionario.toString());
        agenda.setHora(Integer.parseInt(spinnerHorario.toString()));
        agenda.setIdServico(spinnerServico.toString());
        agenda.salvar();
    }


    public String receberData(String data) {
        carregarSpinnerHorario(data);
        return data;
    }

    public void carregarSpinnerHorario(String dataSelecionada) {
        final Calendar calendar = Calendar.getInstance();
        int horaAtual = calendar.get(Calendar.HOUR_OF_DAY);
        String dataAtual = String.valueOf(calendar.get(Calendar.DATE));
        int dataSelec = Integer.parseInt(dataSelecionada);
        int dataAt = Integer.parseInt(dataAtual);
        horasList = new ArrayList<>();
        horasList.clear();
        if (dataSelec > dataAt) {
            for (int i = 7; i <= 20; i++) {
                horasList.add(i);
            }
        } else {
            if (horaAtual > 7 && horaAtual < 20) {
                for (int i = horaAtual + 1; i <= 20; i++) {
                    horasList.add(i);
                }
            } else {
                Toast.makeText(this, "Não existe horarios disponiveis ", Toast.LENGTH_LONG).show();
            }
        }
        ArrayAdapter<Integer> horarios = new ArrayAdapter<>(AgendarActivity.this,
                android.R.layout.simple_spinner_dropdown_item, horasList);
        spinnerHorario.setAdapter(horarios);
    }

    // metodo para retornar datas disponiveis
    public void listarFeriadosNacionais() {
        MyTask myTask = new MyTask();
        myTask.execute(urlApi + "2023");
    }

    // tarefa para consumir a api
    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringURL = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;
            try {
                URL url = new URL(stringURL);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection(); // reponsavel por fazer a conexao
                //recuperar dados em bytes
                inputStream = conexao.getInputStream();
                // lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);
                // objeto utilizado para leitura dos caracteres do InputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";
                //lendo cada linha enquanto estiver linhas a serem exibidas
                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return buffer.toString();
        }


        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            String date = null;
            String name = null;
            String type = null;
            listFeriadosNacionais = new ArrayList<JSONObject>();
            Calendar trasnformarData = new GregorianCalendar();

            JSONObject jsonObject = null;
            JSONArray jsonArray = new JSONArray();
            try {
                jsonArray = new JSONArray(resultado);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    for (int j = 0; j < jsonObject.length(); j++) {
                        if (jsonObject.has("date")) {
                            feriados.add(jsonObject.getString("date"));
                        }
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    // converter a lista de feriado string para Calendar
    public static List<Calendar> converterListStrigFeriadoToCalendarList(List<String> feriadosStrings) {
        List<Calendar> calendarList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (String dateString : feriadosStrings) {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(dateString));
                calendarList.add(calendar);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return calendarList;

    }


    //Metodo para retornar somente as datas disponiveis de segunda a sabado
    private List<Calendar> getDatasDisponiveis() {
        List<Calendar> datasDisponiveis = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                datasDisponiveis.add((Calendar) calendar.clone());
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return datasDisponiveis;
    }

    //Metodo para listar os feriados
    private List<Calendar> getListarDatasFeriados(List<Calendar> datasDisponiveis, List<Calendar> feriados) {

        List<Calendar> dataFinais = new ArrayList<>();

        for (Calendar date : datasDisponiveis) {
            boolean isFeriado = false;

            for (Calendar feriado : feriados) {
                if (date.get(Calendar.YEAR) == feriado.get(Calendar.YEAR) &&
                        date.get(Calendar.MONTH) == feriado.get(Calendar.MONTH) &&
                        date.get(Calendar.DAY_OF_MONTH) == feriado.get(Calendar.DAY_OF_MONTH)) {
                    isFeriado = true;
                    break;
                }
            }
            if (!isFeriado) {
                dataFinais.add(date);
            }
        }
        return dataFinais;

    }


    // retirar dados duplicados
    public <T> List<T> feriadosNacionais(List<T> list) {
        Set<T> set = new HashSet<>();
        List<T> resultList = new ArrayList<>();
        for (T element : list) {
            if (!set.contains(element)) {
                set.add(element);
                resultList.add(element);
            }
        }

        return resultList;
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