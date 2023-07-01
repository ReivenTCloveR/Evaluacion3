package com.example.evaluacion3.Entidades.Gasto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaluacion3.BD.DbGasto;
import com.example.evaluacion3.BD.Gasto;
import com.example.evaluacion3.Entidades.GastosActivity;
import com.example.evaluacion3.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class viewGastoActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView txtNameGasto, txtCostoGasto;
    ImageView IV_Categoria;
    double latitud,longitud;
    Gasto gasto;
    int id = 0;
    String nombregasto, tipo;
    FloatingActionButton fabtn_delet, fabtn_edit;
    Button btn_saveEdit, btn_cancelEdit;


    GoogleMap map;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gasto);

        txtNameGasto = findViewById(R.id.txtNameGasto);
        txtCostoGasto = findViewById(R.id.edittxt_Alimentacion);
        IV_Categoria = findViewById(R.id.IV_Categoria);

        //botones
        fabtn_delet = findViewById(R.id.fabtn_delet);
        fabtn_edit =findViewById(R.id.fabtn_edit);
        btn_saveEdit = findViewById(R.id.btn_saveEdit);
        btn_cancelEdit = findViewById(R.id.btn_cancelEdit);

        //activar visibilidad
        viewActivity();

        //Ver producto
        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                id = Integer.parseInt(null);
            } else {
                id = extra.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbGasto dbGasto = new DbGasto(viewGastoActivity.this);
        gasto = dbGasto.verGasto(id);


        if (gasto != null) {
            txtNameGasto.setText(gasto.getProducto());
            txtCostoGasto.setText(String.valueOf(gasto.getPrecio()));
            tipo = gasto.getTipo();

        }


        if (tipo.equals(getString(R.string.category_1))) {
            IV_Categoria.setImageResource(R.drawable.ic_arriendo);
        } else if (tipo.equals(getString(R.string.category_2))) {
            IV_Categoria.setImageResource(R.drawable.ic_alimentacion);
        } else if (tipo.equals(getString(R.string.category_3))) {
            IV_Categoria.setImageResource(R.drawable.ic_transporte);
        } else if (tipo.equals(getString(R.string.category_4))) {
            IV_Categoria.setImageResource(R.drawable.ic_serviciosbasicos);
        } else if (tipo.equals(getString(R.string.category_5))) {
            IV_Categoria.setImageResource(R.drawable.ic_education);
        } else if (tipo.equals(getString(R.string.category_6))) {
            IV_Categoria.setImageResource(R.drawable.ic_gastos);
        } else if (tipo.equals(getString(R.string.category_7))) {
            IV_Categoria.setImageResource(R.drawable.ic_ahorros);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(viewGastoActivity.this);



        fabtn_delet.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(viewGastoActivity.this);
            builder.setMessage(getString(R.string.deletPrducto)  ).setPositiveButton(getString(R.string.Si), (dialog, i) -> {
                if(dbGasto.eliminarGasto(id)){
                    lista();
                }
            }).setNegativeButton("No", (dialog, i) -> {

            }).show();
        });


        final boolean[] ready = {false};
        String savePrecio = txtCostoGasto.getText().toString();
        fabtn_edit.setOnClickListener(v -> {



            viewEdit();

            btn_saveEdit.setOnClickListener(v1 -> {
                if(!txtCostoGasto.getText().toString().equals("")){

                    int pre = Integer.parseInt(txtCostoGasto.getText().toString());

                    ready[0] = dbGasto.editGasto(id,pre);


                    if (ready[0]){
                        Toast.makeText(viewGastoActivity.this, getString(R.string.editReady), Toast.LENGTH_SHORT).show();
                        viewActivity();
                    }else {
                        Toast.makeText(viewGastoActivity.this,  getString(R.string.editFail), Toast.LENGTH_SHORT).show();
                        viewActivity();
                    }
                }else {
                    Toast.makeText(viewGastoActivity.this, getString(R.string.llenarCampos), Toast.LENGTH_SHORT).show();
                    viewActivity();
                }
            });

            btn_cancelEdit.setOnClickListener(v12 -> {
                txtCostoGasto.setText(savePrecio);
                viewActivity();
                //aqui me falta saber como recuperar los datos anteriores de los editTexT

            });
        });



    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        nombregasto = gasto.getProducto();
        longitud = gasto.getLongitud();
        latitud = gasto.getLatitud();


        LatLng gasto = new LatLng(latitud,longitud);
        map.addMarker(new MarkerOptions().position(gasto).title(nombregasto));
        map.moveCamera(CameraUpdateFactory.newLatLng(gasto));

    }


    private void lista(){
        Intent intent = new Intent(this, GastosActivity.class);
        startActivity(intent);
    }

    private void viewActivity(){

        fabtn_delet.setVisibility(View.VISIBLE);
        fabtn_edit.setVisibility(View.VISIBLE);
        btn_saveEdit.setVisibility(View.GONE);
        btn_cancelEdit.setVisibility(View.GONE);
        txtCostoGasto.setEnabled(false);
    }
    private void viewEdit(){

        fabtn_delet.setVisibility(View.GONE);
        fabtn_edit.setVisibility(View.GONE);
        btn_saveEdit.setVisibility(View.VISIBLE);
        btn_cancelEdit.setVisibility(View.VISIBLE);
        txtCostoGasto.setEnabled(true);
    }

}