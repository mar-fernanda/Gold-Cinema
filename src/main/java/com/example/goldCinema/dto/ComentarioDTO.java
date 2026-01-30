package com.example.goldCinema.dto;

public class ComentarioDTO {

    private Long id;
    private String texto;
    private String fecha;
    private String username;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
