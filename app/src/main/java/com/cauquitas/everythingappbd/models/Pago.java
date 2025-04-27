package com.cauquitas.everythingappbd.models;

public class Pago {
    private boolean confirmado;
    private String fotoPago;
    private String metodo;

    // Default constructor (required for Firebase)
    public Pago() {
    }

    // Constructor with parameters
    public Pago(boolean confirmado, String fotoPago, String metodo) {
        this.confirmado = confirmado;
        this.fotoPago = fotoPago;
        this.metodo = metodo;
    }

    // Getters and setters
    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getFotoPago() {
        return fotoPago;
    }

    public void setFotoPago(String fotoPago) {
        this.fotoPago = fotoPago;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}