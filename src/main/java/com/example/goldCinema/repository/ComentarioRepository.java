package com.example.goldCinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.goldCinema.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    @Query("""
        SELECT c
        FROM Comentario c
        JOIN FETCH c.usuario
        WHERE c.pelicula.imdbId = :imdbId
        ORDER BY c.fecha DESC
    """)
    List<Comentario> findByImdbIdConUsuario(@Param("imdbId") String imdbId);

    List<Comentario> findByPeliculaIdOrderByFechaDesc(Long peliculaId);

    List<Comentario> findByPeliculaImdbIdOrderByFechaDesc(String imdbId);

    @Query("""
        SELECT p.artista
        FROM Comentario c
        JOIN c.pelicula p
        WHERE c.usuario.username = :username
        GROUP BY p.artista
        ORDER BY COUNT(p.artista) DESC
    """)
    List<String> findArtistasFavoritos(@Param("username") String username);
}
