package com.example.evaluacion3.Entidades.Gasto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaluacion3.BD.DbGasto;
import com.example.evaluacion3.R;

public class NewGastoActivity extends AppCompatActivity {

    EditText edittxt_Producto, edittxt_Costo;
    AutoCompleteTextView actxt_categoryitem;
    String[] categoryArray;
    ArrayAdapter<String> adapterItems;
    Button btn_save;

    int valor, total;
    private double latitud;
    private double longitud;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gasto);

        edittxt_Producto = findViewById(R.id.edittxt_Producto);
        edittxt_Costo = findViewById(R.id.edittxt_Costo);
        actxt_categoryitem = findViewById(R.id.actxt_Categoryitem);
        btn_save = findViewById(R.id.btn_saveNewGasto);

        categoryArray = getResources().getStringArray(R.array.opciones_texto);
        adapterItems = new ArrayAdapter<>(this, R.layout.item_list_day, categoryArray);
        actxt_categoryitem.setAdapter(adapterItems);




        int permissionCheck =ContextCompat.checkSelfPermission(NewGastoActivity.this,Manifest.permission.ACCESS_FINE_LOCATION);

        // Verificar y solicitar permisos de ubicación si no están concedidos
        if (permissionCheck != PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(NewGastoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            else {
                ActivityCompat.requestPermissions(NewGastoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }







        btn_save.setOnClickListener(view -> {
            LocationManager locationManager = (LocationManager) NewGastoActivity.this.getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);

            String category = actxt_categoryitem.getText().toString();
            if(latitud !=0 && latitud != 0 && !TextUtils.isEmpty(category)){

                DbGasto dbProducto = new DbGasto(NewGastoActivity.this);
                long id = dbProducto.insertarGasto(edittxt_Producto.getText().toString(),Integer.parseInt(edittxt_Costo.getText().toString()), (int) longitud, (int) latitud, category);

                if (id > 0) {
                    Toast.makeText(this, R.string.addProducto, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.addFail, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
