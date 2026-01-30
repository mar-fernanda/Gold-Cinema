package com.example.goldCinema.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.goldCinema.dto.MovieDTO;
import com.example.goldCinema.model.Comentario;
import com.example.goldCinema.model.Pelicula;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.model.Voto;
import com.example.goldCinema.repository.ComentarioRepository;
import com.example.goldCinema.repository.PeliculaRepository;
import com.example.goldCinema.repository.UsuarioRepository;
import com.example.goldCinema.repository.VotoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MovieService {

    private final PeliculaRepository peliculaRepository;
    private final ComentarioRepository comentarioRepository;
    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RestTemplate restTemplate;

    public MovieService(
            PeliculaRepository peliculaRepository,
            ComentarioRepository comentarioRepository,
            VotoRepository votoRepository,
            UsuarioRepository usuarioRepository,
            RestTemplate restTemplate) {

        this.peliculaRepository = peliculaRepository;
        this.comentarioRepository = comentarioRepository;
        this.votoRepository = votoRepository;
        this.usuarioRepository = usuarioRepository;
        this.restTemplate = restTemplate;
    }
    
    @Value("${omdb.api.key}")
    private String apiKey;


    private Pelicula obtenerOcrearPelicula(String imdbId) {
        return peliculaRepository.findByImdbId(imdbId)
                .orElseGet(() -> {
                    Pelicula p = new Pelicula();
                    p.setImdbId(imdbId);
                    return peliculaRepository.save(p);
                });
    }

    public List<Comentario> obtenerComentarios(String imdbId) {
        return comentarioRepository.findByPeliculaImdbIdOrderByFechaDesc(imdbId);
    }
    public void votar(String imdbId, boolean like, Usuario usuario) {
        Pelicula pelicula = obtenerOcrearPelicula(imdbId);

        Voto voto = votoRepository
                .findByUsuarioAndPelicula(usuario, pelicula)
                .orElse(new Voto());

        voto.setUsuario(usuario);
        voto.setPelicula(pelicula);
        voto.setLikeVoto(like);

        votoRepository.save(voto);
    }

    public double obtenerPorcentajeLikes(String imdbId) {
        Pelicula pelicula = obtenerOcrearPelicula(imdbId);

        List<Voto> votos = votoRepository.findByPelicula(pelicula);
        if (votos.isEmpty()) return 0;

        long likes = votos.stream()
                .filter(Voto::isLikeVoto)
                .count();

        return (likes * 100.0) / votos.size();
    }

    public List<Pelicula> peliculasPorVer(Usuario usuario) {
        return usuario.getPorVer() != null
                ? usuario.getPorVer()
                : new ArrayList<>();
    }

    public void agregarPorVer(Usuario usuario, String imdbId) {
        Pelicula pelicula = obtenerOcrearPelicula(imdbId);

        if (!usuario.getPorVer().contains(pelicula)) {
            usuario.getPorVer().add(pelicula);
            usuarioRepository.save(usuario);
        }
    }

    public List<Pelicula> peliculasVistas(Usuario usuario) {
        return usuario.getVistos() != null
                ? usuario.getVistos()
                : new ArrayList<>();
    }

    public void marcarVisto(Usuario usuario, String imdbId) {
        Pelicula pelicula = obtenerOcrearPelicula(imdbId);

        if (!usuario.getVistos().contains(pelicula)) {
            usuario.getVistos().add(pelicula);
            usuarioRepository.save(usuario);
        }
    }

public Map<String, Long> obtenerGenerosFavoritos(Usuario usuario) {
    List<Pelicula> peliculas = usuario.getFavoritos();

    Map<String, Long> conteoGeneros = peliculas.stream()
            .filter(p -> p.getGenero() != null)
            .flatMap(p -> Arrays.stream(p.getGenero().split(",\\s*")))
            .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

    return conteoGeneros.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
}

    @SuppressWarnings("unchecked")
    public List<MovieDTO> buscarOMDb(String query) {
        try {
            String url = "https://www.omdbapi.com/?apikey=" + apiKey + "&s=" + query;
            String json = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            var map = mapper.readValue(json, java.util.Map.class);

            if ("False".equals(map.get("Response"))) return List.of();

            var search = (List<java.util.Map<String, String>>) map.get("Search");
            if (search == null) return List.of();

            return search.stream().map(m -> {
                MovieDTO dto = new MovieDTO();
                dto.setId(m.get("imdbID"));
                dto.setTitulo(m.get("Title"));
                dto.setImagen(m.get("Poster"));
                dto.setDescripcion("");
                dto.setGenero("");
                dto.setValoracion(0);
                return dto;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public MovieDTO buscarOMDbPorId(String imdbId) {
        try {
            String url = "https://www.omdbapi.com/?apikey=" + apiKey + "&i=" + imdbId;
            String json = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            var map = mapper.readValue(json, java.util.Map.class);

            if ("False".equals(map.get("Response"))) return null;

            MovieDTO dto = new MovieDTO();
            dto.setId(imdbId);
            dto.setTitulo((String) map.get("Title"));
            dto.setDescripcion((String) map.get("Plot"));
            dto.setImagen((String) map.get("Poster"));
            dto.setGenero((String) map.get("Genre"));

            try {
                dto.setValoracion(Double.parseDouble((String) map.get("imdbRating")));
            } catch (Exception e) {
                dto.setValoracion(0);
            }

            return dto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
