package com.example.goldCinema.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.goldCinema.model.Comentario;
import com.example.goldCinema.model.Pelicula;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.repository.ComentarioRepository;
import com.example.goldCinema.repository.PeliculaRepository;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PeliculaRepository peliculaRepository;
    private final UsuarioService usuarioService;

    public ComentarioService(
            ComentarioRepository comentarioRepository,
            PeliculaRepository peliculaRepository,
            UsuarioService usuarioService) {
        this.comentarioRepository = comentarioRepository;
        this.peliculaRepository = peliculaRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Comentario agregarComentario(String imdbId, String texto, Usuario usuario) {
        Pelicula pelicula = obtenerOcrearPelicula(imdbId);

        Comentario comentario = new Comentario();
        comentario.setPelicula(pelicula);
        comentario.setUsuario(usuario);
        comentario.setTexto(texto);
        comentario.setFecha(LocalDateTime.now());

        return comentarioRepository.save(comentario);
    }

    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentarios(Long peliculaId) {
        return comentarioRepository.findByPeliculaIdOrderByFechaDesc(peliculaId);
    }

    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosPorImdb(String imdbId) {
        List<Comentario> comentarios = comentarioRepository.findByImdbIdConUsuario(imdbId);
        System.out.println("Comentarios encontrados para " + imdbId + ": " + comentarios.size());
        return comentarios;
    }

    @Transactional
    public Pelicula obtenerOcrearPelicula(String imdbId) {
        return peliculaRepository.findByImdbId(imdbId)
                .orElseGet(() -> {
                    Pelicula nueva = new Pelicula();
                    nueva.setImdbId(imdbId);
                    return peliculaRepository.save(nueva);
                });
    }
}
