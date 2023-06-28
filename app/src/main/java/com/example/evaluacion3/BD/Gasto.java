package com.example.evaluacion3.BD;

import java.io.Serializable;

public class Gasto implements Serializable {

    private int id;
    private String producto;
    private int precio;
    private double longitud;
    private double latitud;
    private String tipo;

    public Gasto() {

    }

    public Gasto(int id, String producto, int precio, double longitud, double latitud, String tipo) {
        this.producto = producto;
        this.precio = precio;
        this.longitud = longitud;
        this.latitud = latitud;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
