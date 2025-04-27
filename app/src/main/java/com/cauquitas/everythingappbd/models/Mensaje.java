package com.cauquitas.everythingappbd.models;

public class Mensaje {
    private String de;
    private String mensaje;
    private String timestamp;

    // Default constructor (required for Firebase)
    public Mensaje() {
    }

    // Constructor with parameters
    public Mensaje(String de, String mensaje, String timestamp) {
        this.de = de;
        this.mensaje = mensaje;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}