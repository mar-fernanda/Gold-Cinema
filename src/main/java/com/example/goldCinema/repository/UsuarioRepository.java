package com.example.goldCinema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.goldCinema.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("""
        SELECT f.pelicula.genero, COUNT(f)
        FROM Favorito f
        WHERE f.usuario.username = :username
        GROUP BY f.pelicula.genero
        ORDER BY COUNT(f) DESC
    """)
    List<Object[]> generosFavoritos(String username);
}
