package com.example.evaluacion3.NewUser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evaluacion3.R;
import com.example.evaluacion3.ResumenActivity;


public class FormActivity extends AppCompatActivity  {
    EditText editText_Monto, edittxt_Arriendo, edittxt_Alimentacion, edittxt_Transporte, edittxt_Servicios, edittxt_Educacion,edittxt_Deuda,edittxt_Ahorro;
    int montoTotal, dia,nuevaSumaTotal, arriendo, alimentacion, transporte, servicios, educacion, deuda, ahorro, suma;
    Button btn_save;

    String[] item = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };

    AutoCompleteTextView actxt_dayitem;
    ArrayAdapter<String> adapterItems;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //XML --> JAVA
        editText_Monto = findViewById(R.id.editText_MiMonto);
        edittxt_Arriendo = findViewById(R.id.edittxt_Arriendo);
        edittxt_Alimentacion = findViewById(R.id.edittxt_Precio);
        edittxt_Transporte = findViewById(R.id.edittxt_Transporte);
        edittxt_Servicios = findViewById(R.id.edittxt_Costo);
        edittxt_Educacion = findViewById(R.id.edittxt_Educacion);
        edittxt_Deuda = findViewById(R.id.edittxt_Deuda);
        edittxt_Ahorro = findViewById(R.id.edittxt_Ahorro);
        actxt_dayitem = findViewById(R.id.actxt_Midayitem);

        adapterItems = new ArrayAdapter<>(this,R.layout.item_list_day,item);
        actxt_dayitem.setAdapter(adapterItems);

        btn_save = findViewById(R.id.btn_save);


        // Obtener el monto inicial del Intent
        Intent intent = getIntent();
        String montoInicial = intent.getStringExtra("monto");
        montoTotal = Integer.parseInt(montoInicial);

        // Inicializar la suma total
        suma = montoTotal;

        // Mostrar el monto inicial en el EditText editText_Monto
        editText_Monto.setText(String.valueOf(montoTotal));

        // Crear el listener para los EditText de las variables
        TextWatcher variableTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Recalcular la suma total cada vez que cambie un EditText
                arriendo = getInputValue(edittxt_Arriendo);
                alimentacion = getInputValue(edittxt_Alimentacion);
                transporte = getInputValue(edittxt_Transporte);
                servicios = getInputValue(edittxt_Servicios);
                educacion = getInputValue(edittxt_Educacion);
                deuda = getInputValue(edittxt_Deuda);
                ahorro = getInputValue(edittxt_Ahorro);

                // Calcular la nueva suma total
                 nuevaSumaTotal = montoTotal - (arriendo + alimentacion + transporte + servicios + educacion + deuda + ahorro);

                // Actualizar el valor del EditText editText_Monto
                editText_Monto.setText(String.valueOf(nuevaSumaTotal));
            }
            @Override
            public void afterTextChanged(Editable s) {}
            // Método para obtener el valor numérico de un EditText
            private int getInputValue(EditText editText) {
                String input = editText.getText().toString().trim();
                return TextUtils.isEmpty(input) ? 0 : Integer.parseInt(input);
            }
        };
        // Asignar el listener a los EditText de las variables
        edittxt_Arriendo.addTextChangedListener(variableTextWatcher);
        edittxt_Alimentacion.addTextChangedListener(variableTextWatcher);
        edittxt_Transporte.addTextChangedListener(variableTextWatcher);
        edittxt_Servicios.addTextChangedListener(variableTextWatcher);
        edittxt_Educacion.addTextChangedListener(variableTextWatcher);
        edittxt_Deuda.addTextChangedListener(variableTextWatcher);
        edittxt_Ahorro.addTextChangedListener(variableTextWatcher);




        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los EditText y el AutoCompleteTextView


                montoTotal = Integer.parseInt(montoInicial);
                arriendo = getInputValue(edittxt_Arriendo);
                alimentacion = getInputValue(edittxt_Alimentacion);
                transporte = getInputValue(edittxt_Transporte);
                servicios = getInputValue(edittxt_Servicios);
                educacion = getInputValue(edittxt_Educacion);
                deuda = getInputValue(edittxt_Deuda);
                ahorro = getInputValue(edittxt_Ahorro);
                String dayItemText = actxt_dayitem.getText().toString();
                if (!TextUtils.isEmpty(dayItemText)) {
                    dia = Integer.parseInt(dayItemText);
                } else {
                    dia = 0;
                }

                if(nuevaSumaTotal>=0){
                    if(dia != 0){
                        // Guardar los valores en las preferencias
                    SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("Usuario",1);
                    editor.putInt("montoInicial", montoTotal);
                    editor.putInt("arriendo", arriendo);
                    editor.putInt("alimentacion", alimentacion);
                    editor.putInt("transporte", transporte);
                    editor.putInt("servicios", servicios);
                    editor.putInt("educacion", educacion);
                    editor.putInt("deuda", deuda);
                    editor.putInt("ahorro", ahorro);
                    editor.putInt("dia", dia);
                    editor.apply();

                    Intent intent =new Intent(FormActivity.this, ResumenActivity.class);
                    startActivity(intent);
                    finish();

                    }else{Toast.makeText(FormActivity.this, R.string.dia_alerta, Toast.LENGTH_SHORT).show();}
                    
                    
                }else {Toast.makeText(FormActivity.this, R.string.monto_alerta, Toast.LENGTH_SHORT).show();}
              }

            // Método para obtener el valor numérico de un EditText
            private int getInputValue(EditText editText) {
                String input = editText.getText().toString().trim();
                return TextUtils.isEmpty(input) ? 0 : Integer.parseInt(input);
            }
        });

    }


}