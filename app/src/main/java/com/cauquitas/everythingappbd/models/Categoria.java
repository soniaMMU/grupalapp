package com.cauquitas.everythingappbd.models;

public class Categoria {
    private String id; // Key of the category
    private String nombre; // Value of the category

    // Default constructor (required for Firebase)
    public Categoria() {
    }

    // Constructor with parameters
    public Categoria(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}