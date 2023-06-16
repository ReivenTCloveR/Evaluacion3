package com.example.evaluacion3.NewUser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaluacion3.R;

public class WelcomeNewUserActivity extends AppCompatActivity {

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_new_user);

        //add anim
        Animation animUp = AnimationUtils.loadAnimation(this,R.anim.moveup);
        Animation animDown =AnimationUtils.loadAnimation(this,R.anim.movedown);

        TextView txt_Welcome = findViewById(R.id.txt_Welcome);
        Button btn_Welcome = findViewById(R.id.btn_Welcome);

        txt_Welcome.setAnimation(animUp);
        btn_Welcome.setAnimation(animDown);


    }

    public void IrForm(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_content, null);
        builder.setView(dialogView);

        EditText editTextMonto = dialogView.findViewById(R.id.editTextMonto);

        builder.setTitle("Ingrese un Monto");

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            String monto = editTextMonto.getText().toString();
            if(!monto.equals("")){
                Intent intent = new Intent(WelcomeNewUserActivity.this, FormActivity.class);
                intent.putExtra("monto", monto);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, R.string.alerta, Toast.LENGTH_SHORT).show();
            }

        });

        builder.show();
    }




}