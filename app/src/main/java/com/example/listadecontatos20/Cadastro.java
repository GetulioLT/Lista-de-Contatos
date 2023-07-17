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
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        nomeTextCad = findViewById(R.id.nomeTextCad);
        emailTextCad = findViewById(R.id.emailTextCad);
        senhaTextCad = findViewById(R.id.senhaTextCad);
        senhaTextCad.setInputType(129);
        mostrarSenhaCad = findViewById(R.id.mostrarSenhaCad);
        info = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public void voltarMain(View view){
        Intent i = new Intent(getApplicationContext(), Main.class);
        startActivity(i);
    }

    public void mostrarSenhaCad(View view){
        if (mostrarSenhaCad.isChecked()) {
            senhaTextCad.setInputType(1);
        }else{
            senhaTextCad.setInputType(129);
        }
    }
    
    public void cadastrar(View view){
        if (nomeTextCad.getText().toString().isEmpty() &&
        emailTextCad.getText().toString().isEmpty() &&
        senhaTextCad.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), 
                    "Preenchar todos os campos", 
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
                    "Quantidade de caracteres da senha invalidor",
                    Toast.LENGTH_LONG).show();
        }else{
            mAuth.createUserWithEmailAndPassword(emailTextCad.getText().toString(),
                    senhaTextCad.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        myRef = database.getReference("User/" + mAuth.getUid() + "/");

                        info.put("Nome", nomeTextCad.getText().toString());
                        info.put("Email", emailTextCad.getText().toString());
                        info.put("Senha", senhaTextCad.getText().toString());

                        myRef.setValue(info);

                        Toast.makeText(getApplicationContext(),
                                "Cadastro realizado com sucesso",
                                Toast.LENGTH_SHORT).show();

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