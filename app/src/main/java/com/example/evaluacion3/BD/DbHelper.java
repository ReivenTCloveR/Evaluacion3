package com.example.evaluacion3.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "gastos.db";
    public  static final String TABLE_GASTOS = "t_gastos";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_GASTOS + "(" +
                "id_gasto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "producto TEXT NOT NULL," +
                "precio INTEGER NOT NULL," +
                "longitud DOUBLE NOT NULL," +
                "latitud DOUBLE NOT NULL," +
                "tipo TEXT NOT NULL)";
        sqLiteDatabase.execSQL(createTableQuery);

        sqLiteDatabase.execSQL("CREATE TABLE  " + TABLE_GASTOS + "(" +
                "id_gasto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "producto TEXT NOT NULL," +
                "precio INTEGER NOT NULL," +
                "longitud DOUBLE NOT NULL," +
                "latitud DOUBLE NOT NULL," +
                "tipo TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_GASTOS);
    }
}
