package com.example.goldCinema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.goldCinema.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    Optional<Pelicula> findByImdbId(String imdbId);

    List<Pelicula> findByTituloContainingIgnoreCaseOrGeneroContainingIgnoreCase(String titulo, String genero);

    @Query("SELECT f.pelicula FROM Favorito f WHERE f.usuario.username = :username")
    List<Pelicula> findFavoritasDeUsuario(String username);

    @Query("SELECT p FROM Pelicula p WHERE p.id NOT IN " +
           "(SELECT f.pelicula.id FROM Favorito f WHERE f.usuario.username = :username)")
    List<Pelicula> findPorVerDeUsuario(String username);
}
