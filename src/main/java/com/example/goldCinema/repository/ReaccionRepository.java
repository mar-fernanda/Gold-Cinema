package com.example.goldCinema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.goldCinema.model.Pelicula;
import com.example.goldCinema.model.Reaccion;
import com.example.goldCinema.model.Usuario;

public interface ReaccionRepository extends JpaRepository<Reaccion, Long> {

    Optional<Reaccion> findByUsuarioAndPelicula(Usuario usuario, Pelicula pelicula);
}
