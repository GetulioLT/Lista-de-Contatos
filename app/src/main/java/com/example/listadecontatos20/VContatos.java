package com.example.listadecontatos20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VContatos extends AppCompatActivity {
    ListView listaContatos;
    List<String> nomes, telefones, emails;
    static Map<String, String> dadosContatos;
    String[] campos = {"Email", "Telefone"};
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef, dadosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcontatos);
        getSupportActionBar().hide(); // Oculta a barra de ação

        iniciarComponentes(); // Inicializa os componentes da tela

        pegarChaves(); // Obtém as chaves do banco de dados

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AdapterContatos adapter = new AdapterContatos(getApplicationContext(), nomes, emails, telefones); // Cria um novo AdapterContatos
                listaContatos.setAdapter(adapter); // Define o adapter para a ListView de contatos
            }
        }, 1000); // Delay para aguardar a obtenção dos dados

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < nomes.size(); i++){
                    if (i == position){
                        Log.e("dados", nomes.get(position));
                        Log.e("dados", emails.get(position));
                        Log.e("dados", telefones.get(position));
                        dadosContatos.put("Nome", nomes.get(position));
                        dadosContatos.put("Email", emails.get(position));
                        dadosContatos.put("Telefone", telefones.get(position));

                        Intent intent = new Intent(getApplicationContext(), ApagarContatos.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void pegarChaves() {
        myRef = database.getReference("User/" + mAuth.getUid() + "/Contato/"); // Cria uma referência para os contatos do usuário no banco de dados

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    String nome = key.getKey(); // Obtém o nome do contato

                    Log.d("user", nome);

                    nomes.add(nome); // Adiciona o nome à lista de nomes de contatos

                    for (String campo : campos) {
                        dadosRef = database.getReference("User/"
                                + mAuth.getUid() + "/Contato/" + nome + "/" + campo + "/"); // Cria uma referência para os dados do campo (email ou telefone) do contato

                        pegarDados(campo); // Obtém os dados do campo (email ou telefone)
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pegarDados(String campo){
        dadosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valor = snapshot.getValue(String.class); // Obtém o valor do campo (email ou telefone)

                if (campo.equals("Email")){
                    emails.add(valor); // Adiciona o valor à lista de emails
                }else{
                    telefones.add(valor); // Adiciona o valor à lista de telefones
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void voltarHomeContatos(View view){
        Intent i = new Intent(getApplicationContext(), Home.class); // Cria uma nova intenção para a tela Home
        startActivity(i); // Inicia a tela Home
    }

    private void iniciarComponentes() {
        listaContatos = findViewById(R.id.listaContatos); // Obtém a referência da ListView de contatos
        nomes = new ArrayList<>(); // Inicializa a lista de nomes de contatos
        telefones = new ArrayList<>(); // Inicializa a lista de telefones
        emails = new ArrayList<>(); // Inicializa a lista de emails
        dadosContatos = new HashMap<>(); // Inicializa o mapa de dados de contatos
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
    }
}
