package com.example.listadecontatos20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VContatos extends AppCompatActivity {
    ListView listaContatos;
    List<String> nomes, telefones, emails;
    String[] campos = {"Email", "Telefone"};
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef, dadosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcontatos);
        getSupportActionBar().hide();

        iniciarComponentes();

        pegarChaves();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AdapterContatos adapter = new AdapterContatos(getApplicationContext(), nomes, emails, telefones);
                listaContatos.setAdapter(adapter);
            }
        }, 500);
    }

    private void pegarChaves() {
        myRef = database.getReference("User/" + mAuth.getUid() + "/Contato/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot key :
                        snapshot.getChildren()) {
                    String nome = key.getKey();
                    Log.d("user", nome);

                    nomes.add(nome);

                    for (String campo :
                            campos) {
                        dadosRef = database.getReference("User/"
                                + mAuth.getUid() + "/Contato/" + nome + "/" + campo + "/");

                        pegarDados(campo);
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
                String valor = snapshot.getValue(String.class);
                if (campo.equals("Email")){
                    emails.add(valor);
                }else{
                    telefones.add(valor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void voltarHomeContatos(View view){
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);
    }

    private void iniciarComponentes() {
        listaContatos = findViewById(R.id.listaContatos);
        nomes = new ArrayList<>();
        telefones = new ArrayList<>();
        emails = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }
}