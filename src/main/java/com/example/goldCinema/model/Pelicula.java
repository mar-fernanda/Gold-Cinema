package com.example.goldCinema.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pelicula {
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Pelicula)) return false;
    Pelicula pelicula = (Pelicula) o;
    return imdbId != null && imdbId.equals(pelicula.imdbId);
}

@Override
public int hashCode() {
    return imdbId != null ? imdbId.hashCode() : 0;
}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imdbId;
    private String titulo;
    private String genero;
    private String artista;
    private String descripcion;
    private String imagen;
    private double valoracion;

@OneToMany(mappedBy = "pelicula")
private List<Comentario> comentarios = new ArrayList<>();


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImdbId() { return imdbId; }
    public void setImdbId(String imdbId) { this.imdbId = imdbId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public double getValoracion() { return valoracion; }
    public void setValoracion(double valoracion) { this.valoracion = valoracion; }

    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }
}
