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
        getSupportActionBar().hide(); // Oculta a barra de ação

        iniciarComponentes(); // Chama o método para inicializar os componentes da tela
    }

    private void iniciarComponentes() {
        loginTextMain = findViewById(R.id.loginTextMain); // Obtém a referência do campo de login
        senhaTextMain = findViewById(R.id.senhaTextMain); // Obtém a referência do campo de senha
        senhaTextMain.setInputType(129); // Define o tipo de entrada do campo de senha como "texto oculto"
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        mostrarSenhaMain = findViewById(R.id.mostrarSenhaMain); // Obtém a referência da caixa de seleção "mostrar senha"
    }

    public void mostrarSenhaMain(View view) {
        if (mostrarSenhaMain.isChecked()) {
            senhaTextMain.setInputType(1); // Mostra o texto digitado no campo de senha
        } else {
            senhaTextMain.setInputType(129); // Oculta o texto digitado no campo de senha
        }
    }

    public void login(View view) {
        if (loginTextMain.getText().toString().isEmpty() &&
                senhaTextMain.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "É necessário preencher todos os campos",
                    Toast.LENGTH_LONG).show();
        } else if (loginTextMain.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "O campo de email precisa ser preenchido",
                    Toast.LENGTH_LONG).show();
        } else if (senhaTextMain.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "O campo de senha precisa ser preenchido",
                    Toast.LENGTH_LONG).show();
        } else {
            // Tenta fazer o login com o email e senha fornecidos
            mAuth.signInWithEmailAndPassword(loginTextMain.getText().toString(),
                            senhaTextMain.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Se o login for bem-sucedido, exibe uma mensagem de sucesso
                                Log.d("user", "usuário logado");
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                            } else {
                                if (senhaTextMain.getText().toString().toCharArray().length < 6) {
                                    // Se a senha tiver menos de 6 caracteres, exibe uma mensagem de erro
                                    Toast.makeText(getApplicationContext(),
                                            "Senha não possui a quantidade necessária",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    char confir = 'n';
                                    for (char c : loginTextMain.getText().toString().toCharArray()) {
                                        if (c == '@') {
                                            confir = 'y';
                                            break;
                                        }
                                    }

                                    if (confir == 'y') {
                                        // Se o '@' estiver presente no email, exibe uma mensagem de erro de login inexistente
                                        Toast.makeText(getApplicationContext(),
                                                "Login inexistente",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        // Se o '@' não estiver presente no email, exibe uma mensagem de erro de email inexistente
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

    public void cadastro(View view) {
        // Abre a atividade Cadastro
        Intent i = new Intent(getApplicationContext(), Cadastro.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Log.d("user", String.valueOf(currentUser));

        if (currentUser != null) {
            // Se já houver um usuário logado, redireciona para a tela Home
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
        }
    }
}
