package com.example.evaluacion3.Entidades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.evaluacion3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IndicadoresActivity extends AppCompatActivity {


    private TextView textView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicadores);



        textView = findViewById(R.id.txt_informationhttp);

        Button btnHttp1 = findViewById(R.id.btn_http1);
        Button btnHttp2 = findViewById(R.id.btn_http2);
        Button btnHttp3 = findViewById(R.id.btn_http3);


        btnHttp1.setOnClickListener(v -> {
            OkHttpClient client = new OkHttpClient();
            String url ="https://apis.digital.gob.cl/fl/feriados/2023";
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
                                String fecha = feriado.getString("fecha");

                                sb.append("Nombre: ").append(nombre).append("\n");
                                sb.append(fecha).append("\n\n");
                            }

                            String result = sb.toString();

                            IndicadoresActivity.this.runOnUiThread(() -> textView.setText(result));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });

        btnHttp2.setOnClickListener(v -> {
            OkHttpClient client1 = new OkHttpClient();
            String url1 = "https://mindicador.cl/api/dolar";
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
                                String fecha = dataObject.getString("fecha");
                                double valor = dataObject.getDouble("valor");

                                sb1.append("Fecha: ").append(fecha).append("\n");
                                sb1.append("Valor: ").append(valor).append("\n\n");
                            }

                            String result1 = sb1.toString();

                            IndicadoresActivity.this.runOnUiThread(() -> textView.setText(result1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });

        btnHttp3.setOnClickListener(v -> {
            OkHttpClient client1 = new OkHttpClient();
            String url1 = "https://mindicador.cl/api/uf";
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
                                String fecha = dataObject.getString("fecha");
                                double valor = dataObject.getDouble("valor");

                                sb1.append("Fecha: ").append(fecha).append("\n");
                                sb1.append("Valor: ").append(valor).append("\n\n");
                            }

                            String result1 = sb1.toString();

                            IndicadoresActivity.this.runOnUiThread(() -> textView.setText(result1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });


    }
}