package com.cauquitas.everythingappbd.models;

public class AcuerdoEntrega {
    private Compra.Coordenadas coordenadas;
    private String direccionReferencia;
    private String fechaHora;

    // Default constructor (required for Firebase)
    public AcuerdoEntrega() {
    }

    // Constructor with parameters
    public AcuerdoEntrega(Compra.Coordenadas coordenadas, String direccionReferencia, String fechaHora) {
        this.coordenadas = coordenadas;
        this.direccionReferencia = direccionReferencia;
        this.fechaHora = fechaHora;
    }

    // Getters and setters
    public Compra.Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Compra.Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getDireccionReferencia() {
        return direccionReferencia;
    }

    public void setDireccionReferencia(String direccionReferencia) {
        this.direccionReferencia = direccionReferencia;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
