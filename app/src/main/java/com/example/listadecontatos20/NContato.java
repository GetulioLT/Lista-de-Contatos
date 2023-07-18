package com.example.listadecontatos20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NContato extends AppCompatActivity {
    EditText nomeTextCot, telefoneTextCot, emailTextCot;
    Map<String, String> info;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncontato);
        getSupportActionBar().hide(); // Oculta a barra de ação

        iniciarComponentes(); // Inicializa os componentes da tela
    }

    private void iniciarComponentes() {
        nomeTextCot = findViewById(R.id.nomeTextCot); // Obtém a referência do campo de texto do nome do contato
        telefoneTextCot = findViewById(R.id.telefoneTextCot); // Obtém a referência do campo de texto do telefone do contato
        emailTextCot = findViewById(R.id.emailTextCot); // Obtém a referência do campo de texto do email do contato
        info = new HashMap<>(); // Inicializa o mapa para armazenar as informações do contato
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
    }

    public void voltarHome(View view){
        Intent i = new Intent(getApplicationContext(), Home.class); // Cria uma nova intenção para a tela Home
        startActivity(i); // Inicia a tela Home
    }

    public void adicionarContato(View view){
        if (nomeTextCot.getText().toString().isEmpty() ||
                telefoneTextCot.getText().toString().isEmpty() ||
                emailTextCot.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT).show(); // Exibe uma mensagem informando que todos os campos devem ser preenchidos
        } else{
            myRef = database.getReference("User/" + mAuth.getUid() +
                    "/Contato/" + nomeTextCot.getText().toString() + "/"); // Cria uma referência para o contato no banco de dados

            info.put("Email", emailTextCot.getText().toString()); // Adiciona o email ao mapa de informações do contato
            info.put("Telefone", telefoneTextCot.getText().toString()); // Adiciona o telefone ao mapa de informações do contato

            myRef.setValue(info); // Salva as informações do contato no banco de dados

            nomeTextCot.getText().clear(); // Limpa o campo de texto do nome do contato
            emailTextCot.getText().clear(); // Limpa o campo de texto do email do contato
            telefoneTextCot.getText().clear(); // Limpa o campo de texto do telefone do contato
        }
    }
}
