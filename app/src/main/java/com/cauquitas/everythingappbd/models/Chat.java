package com.cauquitas.everythingappbd.models;

public class Chat {
    private String chatId;
    private String productoNombre;
    private String vendedorId;
    private String vendedorNombre;

    public Chat(String chatId, String productoNombre, String vendedorId, String vendedorNombre) {
        this.chatId = chatId;
        this.productoNombre = productoNombre;
        this.vendedorId = vendedorId;
        this.vendedorNombre = vendedorNombre;
    }

    public String getChatId() {
        return chatId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public String getVendedorNombre() {
        return vendedorNombre;
    }
}