package com.example.evaluacion3.Entidades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.evaluacion3.Adapter.Adapter;
import com.example.evaluacion3.BD.DbGasto;
import com.example.evaluacion3.BD.DbHelper;
import com.example.evaluacion3.Entidades.Gasto.NewGastoActivity;
import com.example.evaluacion3.R;

public class GastosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaGasto;
    SearchView seach_gasto;
    Adapter adapter;
    String[] ordenArray;ArrayAdapter<String> adapterItems;
    AutoCompleteTextView actxt_orden;
    String orden;
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

        actxt_orden = findViewById(R.id.actxt_orden);
        ordenArray = getResources().getStringArray(R.array.opciones_orden);
        adapterItems = new ArrayAdapter<>(this, R.layout.item_list_day, ordenArray);
        actxt_orden.setAdapter(adapterItems);

        actxt_orden.setOnItemClickListener((parent, view, position, id) -> {
            String selectedOrder = parent.getItemAtPosition(position).toString();
            // Guardar el valor seleccionado en la variable "orden"
            orden = selectedOrder;
            if (orden != null && orden.equals(ordenArray[0])) {
                adapter.orderFromFirstToLast();
            }else if (orden != null && orden.equals(ordenArray[1])){
                adapter.orderFromLastToFirst();
            } else if (orden != null && orden.equals(ordenArray[2])) {
                adapter.orderByAlphabet();
            }
        });




        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        //SeachBtn
        seach_gasto = findViewById(R.id.seach_gasto);
        seach_gasto.setOnQueryTextListener(this);




    }




    //Metodos de buscar
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtro(newText);
        return false;
    }


    public void IrAddGasto(View view) {
        Intent i = new Intent(this, NewGastoActivity.class);
        startActivity(i);
    }


}