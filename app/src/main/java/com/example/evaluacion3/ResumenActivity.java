package com.example.evaluacion3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.evaluacion3.Entidades.IndicadoresActivity;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.item0Resumen:
                Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item1Presupuesto:
                Toast.makeText(this, "chao", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2Gastos:
                Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3Indicadores:
                Intent intent =new Intent(this, IndicadoresActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}