package com.example.evaluacion3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.evaluacion3.Entidades.GastosActivity;
import com.example.evaluacion3.Entidades.IndicadoresActivity;
import com.example.evaluacion3.Entidades.PresupuestoActivity;

public class ResumenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resumen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.item0Resumen:
                return true;
            case R.id.item1Presupuesto:
                Intent intent1 =new Intent(this, PresupuestoActivity.class);
                startActivity(intent1);
                return true;
            case R.id.item2Gastos:
                Intent intent2 =new Intent(this, GastosActivity.class);
                startActivity(intent2);
                return true;
            case R.id.item3Indicadores:
                Intent intent3 =new Intent(this, IndicadoresActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}