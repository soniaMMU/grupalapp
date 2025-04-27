package com.cauquitas.everythingappbd.models;

import java.util.Map;

public class Tienda {
    private String descripcion;
    private String fotoTienda;
    private String nombreTienda;
    private Map<String, Producto> productos; // Assuming Producto is another class
    private String vendedorId;

    public Tienda() {
        // Default constructor required for calls to DataSnapshot.getValue(Tienda.class)
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoTienda() {
        return fotoTienda;
    }

    public void setFotoTienda(String fotoTienda) {
        this.fotoTienda = fotoTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public Map<String, Producto> getProductos() {
        return productos;
    }

    public void setProductos(Map<String, Producto> productos) {
        this.productos = productos;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }
}