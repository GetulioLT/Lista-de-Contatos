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
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        nomeTextCot = findViewById(R.id.nomeTextCot);
        telefoneTextCot = findViewById(R.id.telefoneTextCot);
        emailTextCot = findViewById(R.id.emailTextCot);
        info = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public void voltarHome(View view){
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);
    }

    public void adicionarContato(View view){
        if (nomeTextCot.getText().toString().isEmpty() ||
                telefoneTextCot.getText().toString().isEmpty() ||
                emailTextCot.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT).show();
        } else{
            myRef = database.getReference("User/" + mAuth.getUid() +
                            "/Contato/" + nomeTextCot.getText().toString() + "/");

            info.put("Email", emailTextCot.getText().toString());
            info.put("Telefone", telefoneTextCot.getText().toString());

            myRef.setValue(info);

            nomeTextCot.getText().clear();
            emailTextCot.getText().clear();
            telefoneTextCot.getText().clear();
        }
    }

}