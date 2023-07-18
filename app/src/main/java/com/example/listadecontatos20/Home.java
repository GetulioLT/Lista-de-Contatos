package com.example.listadecontatos20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    TextView tvHome;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide(); // Oculta a barra de ação

        iniciarComponentes(); // Inicializa os componentes da tela

        tituto(); // Define o título da tela

    }

    private void tituto() {
        myRef = database.getReference("User/" + mAuth.getUid() + "/Nome/"); // Cria uma referência para o nome do usuário no banco de dados

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nome = snapshot.getValue(String.class); // Obtém o valor do nome do usuário do banco de dados

                tvHome.setText(String.format("%s%s", tvHome.getText().toString(), nome)); // Define o texto da TextView com o nome do usuário
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciarComponentes() {
        tvHome = findViewById(R.id.tvHome); // Obtém a referência da TextView
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
    }

    public void novoContato(View view){
        Intent i = new Intent(getApplicationContext(), NContato.class); // Cria uma nova intenção para a tela de Novo Contato
        startActivity(i); // Inicia a tela de Novo Contato
    }

    public  void verContatos(View view){
        Intent i = new Intent(getApplicationContext(), VContatos.class); // Cria uma nova intenção para a tela de Ver Contatos
        startActivity(i); // Inicia a tela de Ver Contatos
    }

    public void deslogar(View view){
        Intent i = new Intent(getApplicationContext(), Main.class); // Cria uma nova intenção para a tela Main (tela de login)
        startActivity(i); // Inicia a tela Main
        FirebaseAuth.getInstance().signOut(); // Realiza o logout do usuário
    }
}
