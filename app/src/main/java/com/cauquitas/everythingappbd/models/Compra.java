package com.cauquitas.everythingappbd.models;

public class Compra {
    private String compradorId;
    private String vendedorId;
    private String productoId;
    private AcuerdoEntrega entrega;
    private String fechaCompra;
    private Pago pago;

    // Default constructor (required for Firebase)
    public Compra() {
    }

    // Constructor with parameters
    public Compra(String compradorId, String vendedorId, String productoId, AcuerdoEntrega entrega, String fechaCompra, Pago pago) {
        this.compradorId = compradorId;
        this.vendedorId = vendedorId;
        this.productoId = productoId;
        this.entrega = entrega;
        this.fechaCompra = fechaCompra;
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

    public AcuerdoEntrega getEntrega() {
        return entrega;
    }

    public void setEntrega(AcuerdoEntrega entrega) {
        this.entrega = entrega;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public class Coordenadas {
    }
}