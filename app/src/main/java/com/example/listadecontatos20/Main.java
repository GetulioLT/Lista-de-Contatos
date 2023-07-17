package com.example.listadecontatos20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main extends AppCompatActivity {
    EditText loginTextMain, senhaTextMain;
    CheckBox mostrarSenhaMain;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        loginTextMain = findViewById(R.id.loginTextMain);
        senhaTextMain = findViewById(R.id.senhaTextMain);
        senhaTextMain.setInputType(129);
        mAuth = FirebaseAuth.getInstance();
        mostrarSenhaMain = findViewById(R.id.mostrarSenhaMain);
    }

    public void mostrarSenhaMain(View view){
        if (mostrarSenhaMain.isChecked()) {
            senhaTextMain.setInputType(1);
        }else{
            senhaTextMain.setInputType(129);
        }
    }

    public void login(View view){
        if (loginTextMain.getText().toString().isEmpty() &&
                senhaTextMain.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "É necessario preencher todos os campos",
                    Toast.LENGTH_LONG).show();
        }else if (loginTextMain.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "O campo de email precisar ser preenchido",
                    Toast.LENGTH_LONG).show();
        } else if (senhaTextMain.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "O campo de senha precisar ser preenchido",
                    Toast.LENGTH_LONG).show();
        }else {
            mAuth.signInWithEmailAndPassword(loginTextMain.getText().toString(),
                            senhaTextMain.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.d("user", "usuario logado");
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                            }
                            else{
                                if (senhaTextMain.getText().toString().toCharArray().length < 6){
                                    Toast.makeText(getApplicationContext(),
                                            "Senha não tem a quantidade necesaria",
                                            Toast.LENGTH_LONG).show();
                                }
                                else{
                                    char confir = 'n';
                                    for (char c :
                                            loginTextMain.getText().toString().toCharArray()) {
                                        if (c == '@'){
                                            confir = 'y';
                                            break;
                                        }
                                    }

                                    if (confir == 'y'){
                                        Toast.makeText(getApplicationContext(),
                                                "Login inexistente",
                                                Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),
                                                "Email não existente",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public void cadastro(View view){
        Intent i = new Intent(getApplicationContext(), Cadastro.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Log.d("user", String.valueOf(currentUser));

        if (currentUser != null){
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
        }

    }
}