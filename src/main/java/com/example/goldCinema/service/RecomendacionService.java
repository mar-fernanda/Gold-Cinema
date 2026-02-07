package com.example.goldCinema.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.goldCinema.dto.MovieDTO;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.repository.UsuarioRepository;

@Service
public class RecomendacionService {

    private final UsuarioRepository usuarioRepository;
    private final MovieApiService movieApiService;
    private final MovieMapper movieMapper;

    private static final List<String> BUSQUEDAS_RANDOM = List.of(
            "love", "war", "future", "dark", "hero",
            "night", "life", "death", "dream", "game",
            "lost", "escape", "secret", "city", "world"
    );

    public RecomendacionService(
            UsuarioRepository usuarioRepository,
            MovieApiService movieApiService,
            MovieMapper movieMapper) {

        this.usuarioRepository = usuarioRepository;
        this.movieApiService = movieApiService;
        this.movieMapper = movieMapper;
    }

    public List<MovieDTO> obtenerRecomendadas(String username) {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow();

        // 🆕 USUARIO NUEVO → RANDOM TOTAL
        if (usuario.getFavoritos() == null || usuario.getFavoritos().isEmpty()) {
            return recomendacionesRandom();
        }

        // 🎯 USUARIO CON GUSTOS
        return recomendacionesPorGustos(usuario);
    }

    private List<MovieDTO> recomendacionesRandom() {

        String keyword = BUSQUEDAS_RANDOM.get(
                (int) (Math.random() * BUSQUEDAS_RANDOM.size())
        );

        String json = movieApiService.buscarPorTitulo(keyword);

        List<MovieDTO> peliculas = movieMapper.fromOmdbSearch(json);

        Collections.shuffle(peliculas);

        return peliculas.stream()
                .limit(10)
                .toList();
    }

    private List<MovieDTO> recomendacionesPorGustos(Usuario usuario) {

        List<String> generos = usuario.getFavoritos().stream()
                .filter(p -> p.getGenero() != null)
                .flatMap(p -> List.of(p.getGenero().split(",")).stream())
                .map(String::trim)
                .distinct()
                .toList();

        if (generos.isEmpty()) {
            return recomendacionesRandom();
        }

        String generoRandom = generos.get(
                (int) (Math.random() * generos.size())
        );

        String json = movieApiService.buscarPorTitulo(generoRandom);

        List<MovieDTO> peliculas = movieMapper.fromOmdbSearch(json);

        Collections.shuffle(peliculas);

        return peliculas.stream()
                .limit(10)
                .toList();
    }
}
