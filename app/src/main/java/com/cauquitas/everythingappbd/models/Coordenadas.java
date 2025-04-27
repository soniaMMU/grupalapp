package com.cauquitas.everythingappbd.models;

public class Coordenadas {
    private double lat;
    private double lng;

    // Default constructor (required for Firebase)
    public Coordenadas() {
    }

    // Constructor with parameters
    public Coordenadas(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    // Getters and setters
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}