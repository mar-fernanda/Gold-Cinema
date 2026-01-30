package com.example.goldCinema.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.goldCinema.dto.MovieDTO;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.service.ComentarioService;
import com.example.goldCinema.service.MovieService;
import com.example.goldCinema.service.UsuarioService;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaApiController {

    private final MovieService movieService;
    private final UsuarioService usuarioService;
    private final ComentarioService comentarioService;

    public PeliculaApiController(MovieService movieService,
                                UsuarioService usuarioService,
                                ComentarioService comentarioService) {
        this.movieService = movieService;
        this.usuarioService = usuarioService;
        this.comentarioService = comentarioService;
    }

    @GetMapping("/buscar")
    public List<MovieDTO> buscar(@RequestParam("q") String q) {
        return movieService.buscarOMDb(q);
    }

    @GetMapping("/{imdbId}")
    public MovieDTO detalle(@PathVariable String imdbId) {
        return movieService.buscarOMDbPorId(imdbId);
    }

    @PostMapping("/{imdbId}/comentarios")
    public ResponseEntity<Void> agregarComentario(
            @PathVariable String imdbId,
            @RequestBody Map<String, String> body,
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String texto = body.get("texto");
        if (texto == null || texto.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        comentarioService.agregarComentario(imdbId, texto.trim(), usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{imdbId}/votar")
    public ResponseEntity<Void> votar(
            @PathVariable String imdbId,
            @RequestParam boolean like,
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        movieService.votar(imdbId, like, usuario);
        return ResponseEntity.ok().build();
    }
}
