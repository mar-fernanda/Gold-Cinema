package com.example.goldCinema.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.goldCinema.dto.MovieDTO;
import com.example.goldCinema.model.Favorito;
import com.example.goldCinema.model.Pelicula;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.repository.FavoritoRepository;
import com.example.goldCinema.repository.PeliculaRepository;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final PeliculaRepository peliculaRepository;

    public FavoritoService(FavoritoRepository favoritoRepository,
                            PeliculaRepository peliculaRepository) {
        this.favoritoRepository = favoritoRepository;
        this.peliculaRepository = peliculaRepository;
    }

    public void agregar(Usuario usuario, String imdbId) {
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        Pelicula pelicula = peliculaRepository.findByImdbId(imdbId)
                .orElseGet(() -> {
                    Pelicula p = new Pelicula();
                    p.setImdbId(imdbId);
                    return peliculaRepository.save(p);
                });

        if (favoritoRepository.findByUsuarioAndPelicula(usuario, pelicula).isPresent()) {
            return;
        }

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setPelicula(pelicula);

        favoritoRepository.save(favorito);
    }


    public void eliminar(Usuario usuario, String imdbId) {
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        peliculaRepository.findByImdbId(imdbId).ifPresent(pelicula ->
            favoritoRepository.findByUsuarioAndPelicula(usuario, pelicula)
                    .ifPresent(favoritoRepository::delete)
        );
    }

    public List<MovieDTO> obtenerFavoritasUsuario(String username, Usuario usuario) {
        List<Favorito> favoritos = favoritoRepository.findByUsuario(usuario);

        return favoritos.stream()
                .map(f -> {
                    Pelicula p = f.getPelicula();
                    MovieDTO dto = new MovieDTO();
                    dto.setId(p.getImdbId());
                    dto.setTitulo(p.getTitulo());
                    dto.setDescripcion(p.getDescripcion());
                    dto.setGenero(p.getGenero());
                    dto.setImagen(p.getImagen());
                    dto.setValoracion(p.getValoracion());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<Favorito> listar(Usuario usuario) {
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        return favoritoRepository.findByUsuario(usuario);
    }
}
