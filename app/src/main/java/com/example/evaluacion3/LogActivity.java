package com.example.evaluacion3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evaluacion3.BD.DbGasto;
import com.example.evaluacion3.BD.DbHelper;
import com.example.evaluacion3.NewUser.WelcomeNewUserActivity;

public class LogActivity extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        //add anim
        Animation animUp = AnimationUtils.loadAnimation(this,R.anim.moveup);
        Animation animDown =AnimationUtils.loadAnimation(this,R.anim.movedown);

        TextView txt_Company = findViewById(R.id.txt_Company);
        @SuppressLint("CutPasteId") TextView txt_Developer = findViewById(R.id.txt_DeveloperName);
        @SuppressLint("CutPasteId") TextView txt_DeveloperName = findViewById(R.id.txt_DeveloperName);
        ImageView img_companyLog = findViewById(R.id.img_companyLog);

        img_companyLog.setAnimation(animUp);
        txt_Company.setAnimation(animUp);
        txt_Developer.setAnimation(animDown);
        txt_DeveloperName.setAnimation(animDown);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        new Handler().postDelayed(() -> {
            if(preferences.contains("Usuario")){
                Intent intent =new Intent(this, ResumenActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent =new Intent(this,WelcomeNewUserActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);




    }
}