package com.cauquitas.everythingappbd.models;

public class Producto {
    private String descripcion;
    private String imagenProducto;
    private String nombre;
    private int precio;
    private String tiendaId;

    // Default constructor (required for Firebase)
    public Producto() {
    }

    // Constructor with parameters
    public Producto(String descripcion, String imagenProducto, String nombre, int precio, String tiendaId) {
        this.descripcion = descripcion;
        this.imagenProducto = imagenProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.tiendaId = tiendaId;
    }

    // Getters and setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(String tiendaId) {
        this.tiendaId = tiendaId;
    }
}