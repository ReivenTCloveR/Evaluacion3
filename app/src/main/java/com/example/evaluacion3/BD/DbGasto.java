package com.example.evaluacion3.BD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbGasto extends DbHelper {
    Context context;
    SQLiteDatabase db;


    public DbGasto(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    public long insertarGasto(String producto, int precio, int longitud, int latitud, String tipo) {

        long id = 0;

        try {
            DbHelper dbhelper = new DbHelper(context);
            this.db = dbhelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("producto", producto);
            values.put("precio", precio);
            values.put("longitud", longitud);
            values.put("latitud", latitud);
            values.put("tipo", tipo);

            id = db.insert(TABLE_GASTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }


    @SuppressLint("Recycle")
    public ArrayList<Gasto> mostrarGasto(){


        DbHelper dbhelper = new DbHelper(context);
        this.db = dbhelper.getWritableDatabase();

        ArrayList<Gasto> listGastos = new ArrayList<>();
        Gasto gasto;
        Cursor cursorProductos;

        cursorProductos = db.rawQuery("SELECT * FROM " + TABLE_GASTOS, null);

        if(cursorProductos.moveToFirst()){
            do{
                gasto = new Gasto();
                gasto.setId(cursorProductos.getInt(0));
                gasto.setProducto(cursorProductos.getString(1));
                gasto.setPrecio(cursorProductos.getInt(2));
                gasto.setLatitud(cursorProductos.getInt(3));
                gasto.setLongitud(cursorProductos.getInt(4));
                gasto.setTipo(cursorProductos.getString(5));
                listGastos.add(gasto);
            }while (cursorProductos.moveToNext());
        }
        return listGastos;
    }


    public Gasto verGasto(int id) {


        DbHelper dbhelper = new DbHelper(context);
        this.db = dbhelper.getWritableDatabase();

        Gasto gasto = null;
        Cursor cursorProductos;

        cursorProductos = db.rawQuery("SELECT * FROM " + TABLE_GASTOS + " WHERE id_gasto = '" + id + "' LIMIT 1", null);

        if (cursorProductos.moveToFirst()) {
            gasto = new Gasto();
            gasto.setId(cursorProductos.getInt(0));
            gasto.setProducto(cursorProductos.getString(1));
            gasto.setPrecio(cursorProductos.getInt(2));
            gasto.setLatitud(cursorProductos.getInt(3));
            gasto.setLongitud(cursorProductos.getInt(4));
            gasto.setTipo(cursorProductos.getString(5));
        }
        cursorProductos.close();
        return gasto;
    }

    public boolean editGasto(int id, String producto, int precio, String tipo) {

        boolean ready;
        DbHelper dbhelper = new DbHelper(context);
        this.db = dbhelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE " + TABLE_GASTOS + " SET producto ='" + producto + "', precio ='" + precio + "', tipo ='" + tipo + "' WHERE id_gasto = '" + id + "'");
            ready = true;
        } catch (Exception ex) {
            ex.toString();
            ready = false;
        }finally {
            db.close();
        }

        return ready;
    }

    public boolean eliminarGasto(int id) {

        boolean ready;
        DbHelper dbhelper = new DbHelper(context);
        this.db = dbhelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_GASTOS + " WHERE id_gasto='" + id + "'");
            ready = true;
        } catch (Exception ex) {
            ex.toString();
            ready = false;
        }finally {
            db.close();
        }

        return ready;
    }



}
