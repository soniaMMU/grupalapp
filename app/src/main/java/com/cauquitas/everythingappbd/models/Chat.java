package com.cauquitas.everythingappbd.models;

import java.util.Map;

public class Chat {
    private String compradorId;
    private String vendedorId;
    private String productoId;
    private AcuerdoEntrega acuerdoEntrega;
    private Map<String, Mensaje> mensajes;
    private Pago pago;

    // Default constructor (required for Firebase)
    public Chat() {
    }

    // Constructor with parameters
    public Chat(String compradorId, String vendedorId, String productoId, AcuerdoEntrega acuerdoEntrega, Map<String, Mensaje> mensajes, Pago pago) {
        this.compradorId = compradorId;
        this.vendedorId = vendedorId;
        this.productoId = productoId;
        this.acuerdoEntrega = acuerdoEntrega;
        this.mensajes = mensajes;
        this.pago = pago;
    }

    // Getters and setters
    public String getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(String compradorId) {
        this.compradorId = compradorId;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public AcuerdoEntrega getAcuerdoEntrega() {
        return acuerdoEntrega;
    }

    public void setAcuerdoEntrega(AcuerdoEntrega acuerdoEntrega) {
        this.acuerdoEntrega = acuerdoEntrega;
    }

    public Map<String, Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(Map<String, Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}