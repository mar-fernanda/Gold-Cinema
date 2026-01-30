package com.example.goldCinema.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(
        name = "usuario_favoritos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "pelicula_id")
    )
    private List<Pelicula> favoritos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "usuario_por_ver",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "pelicula_id")
    )
    private List<Pelicula> porVer = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "usuario_vistos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "pelicula_id")
    )
    private List<Pelicula> vistos = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Pelicula> getFavoritos() { return favoritos; }
    public void setFavoritos(List<Pelicula> favoritos) { this.favoritos = favoritos; }

    public List<Pelicula> getPorVer() { return porVer; }
    public void setPorVer(List<Pelicula> porVer) { this.porVer = porVer; }

    public List<Pelicula> getVistos() { return vistos; }
    public void setVistos(List<Pelicula> vistos) { this.vistos = vistos; }
}
