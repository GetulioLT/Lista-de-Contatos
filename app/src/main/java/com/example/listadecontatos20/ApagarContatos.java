package com.example.listadecontatos20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class ApagarContatos extends AppCompatActivity {
    Map<String, String> dadosContatos;
    TextView tvNomeApagar, tvEmailApagar, tvTelefoneApagar;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference deleteRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_contatos);
        getSupportActionBar().hide(); // Oculta a barra de ação

        iniciarComponentes(); // Inicializa os componentes da tela

        mostrarContato(); // Exibe os detalhes do contato na tela
    }

    private void mostrarContato() {
        tvNomeApagar.setText(dadosContatos.get("Nome")); // Define o nome do contato no TextView correspondente
        tvEmailApagar.setText(dadosContatos.get("Email")); // Define o email do contato no TextView correspondente
        tvTelefoneApagar.setText(dadosContatos.get("Telefone")); // Define o telefone do contato no TextView correspondente
    }

    private void iniciarComponentes() {
        dadosContatos = VContatos.dadosContatos; // Obtém os dados do contato da tela VContatos
        tvNomeApagar = findViewById(R.id.tvNomeApagar); // Obtém a referência do TextView para o nome do contato
        tvEmailApagar = findViewById(R.id.tvEmailApagar); // Obtém a referência do TextView para o email do contato
        tvTelefoneApagar = findViewById(R.id.tvTelefoneApagar); // Obtém a referência do TextView para o telefone do contato
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
    }

    public void apagarContato(View view){
        deleteRef = database.getReference("User/" + mAuth.getUid()
                + "/Contato/" + dadosContatos.get("Nome") + "/"); // Cria uma referência para o contato a ser deletado no banco de dados

        deleteRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Contato deletado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), VContatos.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Deu ruim ai",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
