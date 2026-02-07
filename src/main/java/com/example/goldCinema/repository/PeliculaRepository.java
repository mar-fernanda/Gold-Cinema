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

@Query("""
    SELECT p FROM Pelicula p
    WHERE p.genero IN :generos
    AND p NOT IN (SELECT f FROM Usuario u JOIN u.favoritos f WHERE u.username = :username)
    AND p NOT IN (SELECT v FROM Usuario u JOIN u.vistos v WHERE u.username = :username)
    ORDER BY p.valoracion DESC
""")
List<Pelicula> recomendarPorGeneros(List<String> generos, String username);
List<Pelicula> findTop10ByOrderByValoracionDesc();
List<Pelicula> findTop20ByOrderByValoracionDesc();
}
