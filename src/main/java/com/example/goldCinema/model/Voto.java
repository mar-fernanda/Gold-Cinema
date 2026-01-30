package com.example.goldCinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "votos")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    private boolean likeVoto;

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Pelicula getPelicula() { return pelicula; }
    public boolean isLikeVoto() { return likeVoto; }

    public void setId(Long id) { this.id = id; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }
    public void setLikeVoto(boolean likeVoto) { this.likeVoto = likeVoto; }
}
