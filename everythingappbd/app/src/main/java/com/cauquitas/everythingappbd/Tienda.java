package com.cauquitas.everythingappbd;

public class Tienda {
    private String id;
    private String nombreTienda;
    private String descripcionTienda;
    private String fotoTienda;

    // Constructor vacío para Firebase
    public Tienda() {
        // Necesario para Firebase
    }

    // Constructor con parámetros
    public Tienda(String id, String nombreTienda, String descripcionTienda, String fotoTienda) {
        this.id = id;
        this.nombreTienda = nombreTienda;
        this.descripcionTienda = descripcionTienda;
        this.fotoTienda = fotoTienda;
    }

    // Getters y setters (si es necesario)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getDescripcionTienda() {
        return descripcionTienda;
    }

    public void setDescripcionTienda(String descripcionTienda) {
        this.descripcionTienda = descripcionTienda;
    }

    public String getFotoTienda() {
        return fotoTienda;
    }

    public void setFotoTienda(String fotoTienda) {
        this.fotoTienda = fotoTienda;
    }
}
