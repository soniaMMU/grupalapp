package com.cauquitas.everythingappbd;

public class Comentario {
    private String comentario;
    private float calificacion;

    public Comentario(String comentario, float calificacion) {
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    // Getters y setters
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }
}
