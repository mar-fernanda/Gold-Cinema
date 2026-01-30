package com.example.goldCinema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.goldCinema.model.Favorito;
import com.example.goldCinema.model.Pelicula;
import com.example.goldCinema.model.Usuario;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByUsuario(Usuario usuario);

    Optional<Favorito> findByUsuarioAndPelicula(Usuario usuario, Pelicula pelicula);

    default Optional<Favorito> findByUsuarioAndPeliculaId(Usuario usuario, Long peliculaId) {
        return findByUsuarioAndPelicula(usuario, new Pelicula() {{ setId(peliculaId); }});
    }

    @Query("SELECT f.pelicula.genero FROM Favorito f " +
        "WHERE f.usuario.username = :username " +
        "ORDER BY COUNT(f.pelicula.genero) DESC")
    List<String> findGenerosFavoritos(@Param("username") String username);
}
