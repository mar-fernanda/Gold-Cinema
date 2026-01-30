package com.example.goldCinema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.goldCinema.model.Pelicula;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.model.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByUsuarioAndPelicula(Usuario usuario, Pelicula pelicula);
    List<Voto> findByPelicula(Pelicula pelicula);
}

