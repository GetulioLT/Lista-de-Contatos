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
        getSupportActionBar().hide();

        iniciarComponentes();

        titutlo();

    }

    private void titutlo() {
        myRef = database.getReference("User/" + mAuth.getUid() + "/Nome/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nome = snapshot.getValue(String.class);

                tvHome.setText(String.format("%s%s", tvHome.getText().toString(), nome));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciarComponentes() {
        tvHome = findViewById(R.id.tvHome);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public void novoContato(View view){
        Intent i = new Intent(getApplicationContext(), NContato.class);
        startActivity(i);
    }

    public  void verContatos(View view){
        Intent i = new Intent(getApplicationContext(), VContatos.class);
        startActivity(i);
    }

    public void deslogar(View view){
        Intent i = new Intent(getApplicationContext(), Main.class);
        startActivity(i);
        FirebaseAuth.getInstance().signOut();
    }
}