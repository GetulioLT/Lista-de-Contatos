package com.example.listadecontatos20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {
    EditText nomeTextCad, emailTextCad, senhaTextCad;
    CheckBox mostrarSenhaCad;

    Map<String, String> info;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide(); // Oculta a barra de ação

        iniciarComponentes(); // Chama o método para inicializar os componentes da tela
    }

    private void iniciarComponentes() {
        nomeTextCad = findViewById(R.id.nomeTextCad); // Obtém a referência do campo de nome
        emailTextCad = findViewById(R.id.emailTextCad); // Obtém a referência do campo de email
        senhaTextCad = findViewById(R.id.senhaTextCad); // Obtém a referência do campo de senha
        senhaTextCad.setInputType(129); // Define o tipo de entrada do campo de senha como "texto oculto"
        mostrarSenhaCad = findViewById(R.id.mostrarSenhaCad); // Obtém a referência da caixa de seleção "mostrar senha"
        info = new HashMap<>(); // Cria um mapa para armazenar as informações do usuário
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
    }

    public void voltarMain(View view) {
        Intent i = new Intent(getApplicationContext(), Main.class);
        startActivity(i);
    }

    public void mostrarSenhaCad(View view) {
        if (mostrarSenhaCad.isChecked()) {
            senhaTextCad.setInputType(1); // Mostra o texto digitado no campo de senha
        } else {
            senhaTextCad.setInputType(129); // Oculta o texto digitado no campo de senha
        }
    }

    public void cadastrar(View view) {
        if (nomeTextCad.getText().toString().isEmpty() &&
                emailTextCad.getText().toString().isEmpty() &&
                senhaTextCad.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos",
                    Toast.LENGTH_LONG).show();
        } else if (nomeTextCad.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha o campo de nome",
                    Toast.LENGTH_LONG).show();
        } else if (emailTextCad.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha o campo de email",
                    Toast.LENGTH_LONG).show();
        } else if (senhaTextCad.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha o campo de senha",
                    Toast.LENGTH_LONG).show();
        } else if (senhaTextCad.getText().toString().toCharArray().length < 6) {
            Toast.makeText(getApplicationContext(),
                    "Quantidade de caracteres da senha inválida",
                    Toast.LENGTH_LONG).show();
        } else {
            // Tenta criar um novo usuário com o email e senha fornecidos
            mAuth.createUserWithEmailAndPassword(emailTextCad.getText().toString(),
                            senhaTextCad.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                myRef = database.getReference("User/" + mAuth.getUid() + "/"); // Cria uma referência para o usuário no banco de dados

                                info.put("Nome", nomeTextCad.getText().toString()); // Adiciona o nome ao mapa de informações
                                info.put("Email", emailTextCad.getText().toString()); // Adiciona o email ao mapa de informações
                                info.put("Senha", senhaTextCad.getText().toString()); // Adiciona a senha ao mapa de informações

                                myRef.setValue(info); // Salva as informações do usuário no banco de dados

                                Toast.makeText(getApplicationContext(),
                                        "Cadastro realizado com sucesso",
                                        Toast.LENGTH_SHORT).show();

                                // Define um atraso de 2 segundos antes de redirecionar para a tela Main
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), Main.class);
                                        startActivity(i);
                                    }
                                }, 2000);
                            }
                        }
                    });
        }
    }
}
