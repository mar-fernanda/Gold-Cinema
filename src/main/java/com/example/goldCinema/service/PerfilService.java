package com.example.goldCinema.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.goldCinema.repository.ComentarioRepository;
import com.example.goldCinema.repository.FavoritoRepository;
import com.example.goldCinema.repository.ReaccionRepository;

@Service
public class PerfilService {

    private final FavoritoRepository favoritoRepository;
    private final ComentarioRepository comentarioRepository;
    private final ReaccionRepository reaccionRepository;

    public PerfilService(FavoritoRepository favoritoRepository,
                        ComentarioRepository comentarioRepository,
                        ReaccionRepository reaccionRepository) {
        this.favoritoRepository = favoritoRepository;
        this.comentarioRepository = comentarioRepository;
        this.reaccionRepository = reaccionRepository;
    }

    public List<String> generosFavoritos(String username) {
        return favoritoRepository.findGenerosFavoritos(username);
    }

    public List<String> artistasFavoritos(String username) {
        return comentarioRepository.findArtistasFavoritos(username);
    }
}

    