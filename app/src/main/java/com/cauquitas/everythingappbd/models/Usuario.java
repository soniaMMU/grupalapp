package com.cauquitas.everythingappbd.models;

public class Usuario {
    private String clave;
    private String correo;
    private String fotoPerfil;
    private String nombre;
    private String telefono;
    private String tiendaId;

    // Default constructor (required for Firebase)
    public Usuario() {
    }

    // Constructor with parameters
    public Usuario(String clave, String correo, String fotoPerfil, String nombre, String telefono, String tiendaId) {
        this.clave = clave;
        this.correo = correo;
        this.fotoPerfil = fotoPerfil;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tiendaId = tiendaId;
    }

    // Getters and setters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(String tiendaId) {
        this.tiendaId = tiendaId;
    }
}