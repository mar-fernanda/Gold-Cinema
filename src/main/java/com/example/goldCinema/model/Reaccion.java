package com.example.goldCinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reacciones")
public class Reaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;

    public enum Tipo {
        LIKE, DISLIKE
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Pelicula getPelicula() { return pelicula; }
    public Tipo getTipo() { return tipo; }

    public void setId(Long id) { this.id = id; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
}
