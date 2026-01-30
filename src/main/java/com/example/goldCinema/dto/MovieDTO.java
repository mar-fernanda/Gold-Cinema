package com.example.goldCinema.dto;

public class MovieDTO {
    private String id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private String genero;
    private double valoracion;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public double getValoracion() { return valoracion; }
    public void setValoracion(double valoracion) { this.valoracion = valoracion; }
    private String actores;
}
