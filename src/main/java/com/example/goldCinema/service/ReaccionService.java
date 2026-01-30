package com.example.goldCinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goldCinema.model.Pelicula;
import com.example.goldCinema.model.Reaccion;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.repository.PeliculaRepository;
import com.example.goldCinema.repository.ReaccionRepository;

@Service
public class ReaccionService {

    @Autowired
    private ReaccionRepository reaccionRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PeliculaRepository peliculaRepository;

    public void reaccionar(Long peliculaId, Reaccion.Tipo tipo) {

        Usuario usuario = usuarioService.obtenerUsuarioAutenticado();
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        Pelicula pelicula = peliculaRepository.findById(peliculaId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        reaccionRepository.findByUsuarioAndPelicula(usuario, pelicula)
                .ifPresent(reaccionRepository::delete);

        Reaccion r = new Reaccion();
        r.setUsuario(usuario);
        r.setPelicula(pelicula);
        r.setTipo(tipo);

        reaccionRepository.save(r);
    }
}
