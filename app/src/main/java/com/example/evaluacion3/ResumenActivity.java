package com.example.evaluacion3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.evaluacion3.BD.DbGasto;
import com.example.evaluacion3.BD.DbHelper;
import com.example.evaluacion3.Entidades.GastosActivity;
import com.example.evaluacion3.Entidades.IndicadoresActivity;
import com.example.evaluacion3.Entidades.PresupuestoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResumenActivity extends AppCompatActivity {

    TextView txthttpFeriado, txthttpDolar, txthttpUF;
    EditText txtResumenArriendp,txtResumenAlimentacion, txtResumenTransport, edittxt_Costo, txtResumenEducacion, txtResumenAhorro, txtResumenDeuda;
    String[] categoryArray;
    ImageView alert1, alert2, alert3, alert4, alert5, alert6, alert7;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        txthttpFeriado = findViewById(R.id.txthttpFeriado);
        txthttpDolar = findViewById(R.id.txthttpDolar);
        txthttpUF = findViewById(R.id.txthttpUF);

        txtResumenArriendp = findViewById(R.id.txtResumenArriendp);
        txtResumenAlimentacion = findViewById(R.id.txtResumenAlimentacion);
        txtResumenTransport = findViewById(R.id.txtResumenTransport);
        edittxt_Costo = findViewById(R.id.edittxt_Costo);
        txtResumenEducacion = findViewById(R.id.txtResumenEducacion);
        txtResumenDeuda = findViewById(R.id.txtResumenDeuda);
        txtResumenAhorro = findViewById(R.id.txtResumenAhorro);

        alert1 = findViewById(R.id.alert1);
        alert2 = findViewById(R.id.alert2);
        alert3 = findViewById(R.id.alert3);
        alert4 = findViewById(R.id.alert4);
        alert5 = findViewById(R.id.alert5);
        alert6 = findViewById(R.id.alert6);
        alert7 = findViewById(R.id.alert7);

        deshabilitarAlert();

        //instanciar la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        //RECUPERAR LOS DATOS DE LAS PREFERENCIAS
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        int arriendo = preferences.getInt("arriendo", 0);
        int alimentacion = preferences.getInt("alimentacion", 0);
        int transporte = preferences.getInt("transporte", 0);
        int servicios = preferences.getInt("servicios", 0);
        int educacion = preferences.getInt("educacion", 0);
        int deuda = preferences.getInt("deuda", 0);
        int ahorro = preferences.getInt("ahorro", 0);


        categoryArray = getResources().getStringArray(R.array.opciones_texto);

        DbGasto dbGasto = new DbGasto(ResumenActivity.this);
        int gastoArriendo = dbGasto.getTotalByType(categoryArray[0]);
        int gastoAlimentacion = dbGasto.getTotalByType(categoryArray[1]);
        int gastoTransporte = dbGasto.getTotalByType(categoryArray[2]);
        int gastoServicio = dbGasto.getTotalByType(categoryArray[3]);
        int gastoEducacion = dbGasto.getTotalByType(categoryArray[4]);
        int gastoDeuda = dbGasto.getTotalByType(categoryArray[5]);
        int gastoAhorro = dbGasto.getTotalByType(categoryArray[6]);


        String Cadena1 = arriendo+" / "+gastoArriendo;
        String Cadena2 = alimentacion+" / "+gastoAlimentacion;
        String Cadena3 = transporte+" / "+gastoTransporte;
        String Cadena4 = servicios+" / "+gastoServicio;
        String Cadena5 = educacion+" / "+gastoEducacion;
        String Cadena6 = deuda+" / "+gastoDeuda;
        String Cadena7 = ahorro+" / "+gastoAhorro;




        //MOSTRAR LOS VALORES GUARDADOS
        txtResumenArriendp.setText(Cadena1);
        txtResumenAlimentacion.setText(Cadena2);
        txtResumenTransport.setText(Cadena3);
        edittxt_Costo.setText(Cadena4);
        txtResumenEducacion.setText(Cadena5);
        txtResumenDeuda.setText(Cadena6);
        txtResumenAhorro.setText(Cadena7);

        //MOSTRAR ALERTA
        if(arriendo<gastoArriendo){
            alert1.setVisibility(View.VISIBLE);
            mostrarDialogoExcesoCategoria();
        }if(alimentacion<gastoAlimentacion){
            alert2.setVisibility(View.VISIBLE);
            mostrarDialogoExcesoCategoria();
        }if(transporte<gastoTransporte){
            alert3.setVisibility(View.VISIBLE);
            mostrarDialogoExcesoCategoria();
        }if(servicios<gastoServicio){
            alert4.setVisibility(View.VISIBLE);
            mostrarDialogoExcesoCategoria();
        }if(educacion<gastoEducacion){
            alert5.setVisibility(View.VISIBLE);
            mostrarDialogoExcesoCategoria();
        }if(deuda<gastoDeuda){
            alert6.setVisibility(View.VISIBLE);
            mostrarDialogoExcesoCategoria();
        }if(ahorro<gastoAhorro){
            alert7.setVisibility(View.VISIBLE);
            mostrarDialogoExcesoCategoria();
        }


        //Mostrar HTTPRequest
        mostrarFeriado();
        mostrarDolar();
        mostrarUF();
    }


    public void deshabilitarAlert(){

        alert1.setVisibility(View.GONE);
        alert2.setVisibility(View.GONE);
        alert3.setVisibility(View.GONE);
        alert4.setVisibility(View.GONE);
        alert5.setVisibility(View.GONE);
        alert6.setVisibility(View.GONE);
        alert7.setVisibility(View.GONE);
    }

    public void mostrarDialogoExcesoCategoria() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alertafocategoria)
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.dismiss(); // Cierra el diálogo
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    public void mostrarUF(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = dateFormat.format(calendar.getTime());

        OkHttpClient client1 = new OkHttpClient();
        String url1 = "https://mindicador.cl/api/uf/"+fechaActual;
        Request request1 = new Request.Builder().url(url1).build();


        client1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response1) throws IOException {
                if (response1.isSuccessful()) {
                    String myResponse = response1.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(myResponse);
                        JSONArray serieArray = jsonObject.getJSONArray("serie");

                        StringBuilder sb1 = new StringBuilder();
                        for (int i = 0; i < serieArray.length(); i++) {
                            JSONObject dataObject = serieArray.getJSONObject(i);
                            double valor = dataObject.getDouble("valor");
                            sb1.append(valor).append("\n");
                        }

                        String result1 = sb1.toString();

                        ResumenActivity.this.runOnUiThread(() -> txthttpUF.setText(result1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void mostrarDolar(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = dateFormat.format(calendar.getTime());

        OkHttpClient client1 = new OkHttpClient();
        String url1 = "https://mindicador.cl/api/dolar/"+fechaActual;
        Request request1 = new Request.Builder().url(url1).build();


        client1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response1) throws IOException {
                if (response1.isSuccessful()) {
                    String myResponse = response1.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(myResponse);
                        JSONArray serieArray = jsonObject.getJSONArray("serie");

                        StringBuilder sb1 = new StringBuilder();
                        for (int i = 0; i < serieArray.length(); i++) {
                            JSONObject dataObject = serieArray.getJSONObject(i);
                            double valor = dataObject.getDouble("valor");
                            sb1.append(valor).append("\n");
                        }

                        String result1 = sb1.toString();

                        ResumenActivity.this.runOnUiThread(() -> txthttpDolar.setText(result1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public void mostrarFeriado(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        String fechaActual = dateFormat.format(calendar.getTime());

        OkHttpClient client = new OkHttpClient();
        String url ="https://apis.digital.gob.cl/fl/feriados/"+fechaActual;
        Request request = new Request.Builder().url(url).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(myResponse);
                        StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject feriado = jsonArray.getJSONObject(i);
                            String nombre = feriado.getString("nombre");
                            sb.append(nombre).append("\n");
                        }

                        String result = sb.toString();

                        ResumenActivity.this.runOnUiThread(() -> txthttpFeriado.setText(result));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


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
        DbGasto dbGasto = new DbGasto(this);
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
            case R.id.item4DeletAcc:
                AlertDialog.Builder builder = new AlertDialog.Builder(ResumenActivity.this);
                builder.setTitle(R.string.EliminarCuenta)
                        .setMessage(R.string.SeguroEliminar)
                        .setPositiveButton(R.string.Si, (dialog, which) -> {
                            SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.remove("Usuario");
                            editor.apply();
                            dbGasto.deleteAllFromTable("t_gastos");
                            Intent intent = new Intent(this, LogActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}