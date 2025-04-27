package com.cauquitas.everythingappbd;

public class Product {
    private String nombre;
    private String imagen;
    private double precio;

    public Product() {
        // Constructor vacío requerido por Firestore
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
