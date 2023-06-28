package com.example.evaluacion3.Entidades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.evaluacion3.Adapter.Adapter;
import com.example.evaluacion3.BD.DbGasto;
import com.example.evaluacion3.BD.DbHelper;
import com.example.evaluacion3.Entidades.Gasto.NewGastoActivity;
import com.example.evaluacion3.R;

public class GastosActivity extends AppCompatActivity {

    RecyclerView listaGasto;
    SearchView svBuscar;
    Adapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);


        listaGasto = findViewById(R.id.rv_gasto);
        listaGasto.setLayoutManager(new LinearLayoutManager(this));
        DbGasto dbGasto = new DbGasto(GastosActivity.this);
        adapter = new Adapter(this, dbGasto.mostrarGasto());
        listaGasto.setAdapter(adapter);



        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


    }


    public void IrAddGasto(View view) {
        Intent i = new Intent(this, NewGastoActivity.class);
        startActivity(i);    }

}