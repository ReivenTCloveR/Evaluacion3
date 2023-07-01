package com.example.evaluacion3.Entidades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaluacion3.R;
import com.example.evaluacion3.ResumenActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class PresupuestoActivity extends AppCompatActivity {

    EditText editText_MiMonto, edittxt_MiArriendo, edittxt_MiAlimentacion,edittxt_MiTransporte, edittxt_MiServicios, edittxt_MiEducacion, edittxt_Deuda, edittxt_Ahorro;

    String[] item = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };

    AutoCompleteTextView actxt_Midayitem;
    ArrayAdapter<String> adapterItems;

    FloatingActionButton floatbtn_edit;
    Button btn_save_change;
    TextView txtTotal, txt_MontoNoDestinado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuesto);

        //EDIT TEXT
        editText_MiMonto = findViewById(R.id.editText_MiMonto);
        edittxt_MiArriendo = findViewById(R.id.edittxt_MiArriendo);
        edittxt_MiAlimentacion = findViewById(R.id.edittxt_MiAlimentacion);
        edittxt_MiTransporte = findViewById(R.id.edittxt_MiTransporte);
        edittxt_MiServicios = findViewById(R.id.edittxt_MiServicios);
        edittxt_MiEducacion = findViewById(R.id.edittxt_MiEducacion);
        edittxt_Deuda = findViewById(R.id.edittxt_Deuda);
        edittxt_Ahorro = findViewById(R.id.txtResumenAhorro);
        actxt_Midayitem = findViewById(R.id.actxt_Midayitem);

        txtTotal = findViewById(R.id.txtTotal);
        txt_MontoNoDestinado = findViewById(R.id.txt_MontoNoDestinado);
        floatbtn_edit = findViewById(R.id.floatbtn_edit);
        btn_save_change = findViewById(R.id.btn_save_change);
        //ESTADO DE LOS EDITXT
        deshabilitaEditText();





        //RECUPERAR LOS DATOS DE LAS PREFERENCIAS
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        int montoInicial = preferences.getInt("montoInicial", 0);
        int arriendo = preferences.getInt("arriendo", 0);
        int alimentacion = preferences.getInt("alimentacion", 0);
        int transporte = preferences.getInt("transporte", 0);
        int servicios = preferences.getInt("servicios", 0);
        int educacion = preferences.getInt("educacion", 0);
        int deuda = preferences.getInt("deuda", 0);
        int ahorro = preferences.getInt("ahorro", 0);
        int dia = preferences.getInt("dia", 0);


        //MOSTRAR LOS VALORES GUARDADOS
        editText_MiMonto.setText(String.valueOf(montoInicial));
        edittxt_MiArriendo.setText(String.valueOf(arriendo));
        edittxt_MiAlimentacion.setText(String.valueOf(alimentacion));
        edittxt_MiTransporte.setText(String.valueOf(transporte));
        edittxt_MiServicios.setText(String.valueOf(servicios));
        edittxt_MiEducacion.setText(String.valueOf(educacion));
        edittxt_Deuda.setText(String.valueOf(deuda));
        edittxt_Ahorro.setText(String.valueOf(ahorro));
        actxt_Midayitem.setText(String.valueOf(dia));



        floatbtn_edit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.alertaforedit))
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Acciones a realizar al hacer clic en el botón OK del diálogo
                        habilitaEditText();
                        dialog.dismiss(); // Cierra el diálogo
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        btn_save_change.setOnClickListener(v -> {

            int montoInicial1 = getInputValue(editText_MiMonto);
            int arriendo1 = getInputValue(edittxt_MiArriendo);
            int alimentacion1 = getInputValue(edittxt_MiAlimentacion);
            int transporte1 = getInputValue(edittxt_MiTransporte);
            int servicios1 = getInputValue(edittxt_MiServicios);
            int educacion1 = getInputValue(edittxt_MiEducacion);
            int deuda1 = getInputValue(edittxt_Deuda);
            int ahorro1 = getInputValue(edittxt_Ahorro);
            int dia1 = Integer.parseInt(actxt_Midayitem.getText().toString());

            int suma = (arriendo1 + alimentacion1 + transporte1 + servicios1 + educacion1 + deuda1 + ahorro1);

            if(montoInicial1 >= suma){
                SharedPreferences preferences1 = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putInt("montoInicial", montoInicial1);
                editor.putInt("arriendo", arriendo1);
                editor.putInt("alimentacion", alimentacion1);
                editor.putInt("transporte", transporte1);
                editor.putInt("servicios", servicios1);
                editor.putInt("educacion", educacion1);
                editor.putInt("deuda", deuda1);
                editor.putInt("ahorro", ahorro1);
                editor.putInt("dia", dia1);
                editor.apply();

                Intent intent =new Intent(this, ResumenActivity.class);
                startActivity(intent);
                finish();

            }else {
                Toast.makeText(PresupuestoActivity.this, R.string.monto_alerta, Toast.LENGTH_SHORT).show();
            }

        });


        calcularTotal();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calcularTotal();
            }
        };


        edittxt_MiArriendo.addTextChangedListener(textWatcher);
        edittxt_MiAlimentacion.addTextChangedListener(textWatcher);
        edittxt_MiTransporte.addTextChangedListener(textWatcher);
        edittxt_MiServicios.addTextChangedListener(textWatcher);
        edittxt_MiEducacion.addTextChangedListener(textWatcher);
        edittxt_Deuda.addTextChangedListener(textWatcher);
        edittxt_Ahorro.addTextChangedListener(textWatcher);


    }//CLOSE ONCREATE

    private void calcularTotal() {
        int total = getInputValue(editText_MiMonto);
        int arriendo = getInputValue(edittxt_MiArriendo);
        int alimentacion = getInputValue(edittxt_MiAlimentacion);
        int transporte = getInputValue(edittxt_MiTransporte);
        int servicios = getInputValue(edittxt_MiServicios);
        int educacion = getInputValue(edittxt_MiEducacion);
        int deuda = getInputValue(edittxt_Deuda);
        int ahorro = getInputValue(edittxt_Ahorro);
        int suma = total- (arriendo + alimentacion + transporte + servicios + educacion + deuda + ahorro);
        txtTotal.setText(String.valueOf(suma));
    }

    private int getInputValue(EditText editText) {
        String input = editText.getText().toString().trim();
        return TextUtils.isEmpty(input) ? 0 : Integer.parseInt(input);
    }


    private void habilitaEditText() {

        editText_MiMonto.setEnabled(true);
        edittxt_MiArriendo.setEnabled(true);
        edittxt_MiAlimentacion.setEnabled(true);
        edittxt_MiTransporte.setEnabled(true);
        edittxt_MiServicios.setEnabled(true);
        edittxt_MiEducacion.setEnabled(true);
        edittxt_Deuda.setEnabled(true);
        edittxt_Ahorro.setEnabled(true);
        actxt_Midayitem.setEnabled(true);
        btn_save_change.setVisibility(View.VISIBLE);
        floatbtn_edit.setVisibility(View.GONE);
        txtTotal.setVisibility(View.VISIBLE);
        txt_MontoNoDestinado.setVisibility(View.VISIBLE);
        adapterItems = new ArrayAdapter<>(this,R.layout.item_list_day,item);
        actxt_Midayitem.setAdapter(adapterItems);

        Animation animacion = AnimationUtils.loadAnimation(this,R.anim.movedown);
        btn_save_change.setAnimation(animacion);
    }

    private void deshabilitaEditText() {

        editText_MiMonto.setEnabled(false);
        edittxt_MiArriendo.setEnabled(false);
        edittxt_MiAlimentacion.setEnabled(false);
        edittxt_MiTransporte.setEnabled(false);
        edittxt_MiServicios.setEnabled(false);
        edittxt_MiEducacion.setEnabled(false);
        edittxt_Deuda.setEnabled(false);
        edittxt_Ahorro.setEnabled(false);
        actxt_Midayitem.setEnabled(false);
        btn_save_change.setVisibility(View.GONE);
        txtTotal.setVisibility(View.GONE);
        txt_MontoNoDestinado.setVisibility(View.GONE);
    }
}