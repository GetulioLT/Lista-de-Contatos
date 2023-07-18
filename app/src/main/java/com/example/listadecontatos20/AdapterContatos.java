package com.example.listadecontatos20;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterContatos extends BaseAdapter{
    Context context;
    List<String> nomes;
    List<String> emails;
    List<String> telefones;
    LayoutInflater inflater;

    public AdapterContatos(Context context, List<String> nomes, List<String> emails, List<String> telefones) {
        this.context = context;
        this.nomes = nomes;
        this.emails = emails;
        this.telefones = telefones;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nomes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adaptercontatos, null);

        TextView tvnome = convertView.findViewById(R.id.nomeAdapter);
        TextView tvemail = convertView.findViewById(R.id.emailAdapter);
        TextView tvtelefone = convertView.findViewById(R.id.telefoneAdapter);

        tvnome.setText(nomes.get(position));
        Log.e("user", nomes.get(position));
        tvemail.setText(emails.get(position));
        Log.e("user", emails.get(position));
        tvtelefone.setText(telefones.get(position));
        Log.e("user", telefones.get(position));

        return convertView;
    }
}
