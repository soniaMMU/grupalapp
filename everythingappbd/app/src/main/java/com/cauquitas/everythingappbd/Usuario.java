package com.cauquitas.everythingappbd;

public class Usuario {
    private String nombre, correo, clave, telefono, fotoPerfil, tiendald;

    public Usuario() {
        // Constructor vacío requerido para Firebase
    }

    public Usuario(String nombre, String correo, String clave, String telefono, String fotoPerfil) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.telefono = telefono;
        this.fotoPerfil = fotoPerfil;
        this.tiendald = ""; // todavía no tiene tienda
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getTiendald() {
        return tiendald;
    }

    // Setters (opcional si quieres modificar los valores después)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void setTiendald(String tiendald) {
        this.tiendald = tiendald;
    }
}
