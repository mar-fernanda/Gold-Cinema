package com.example.goldCinema.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.goldCinema.dto.MovieDTO;
import com.example.goldCinema.service.MovieService;

@RestController
@RequestMapping("/api/buscar")
public class BuscadorController {

    private final MovieService movieService;

    public BuscadorController(MovieService movieService) {
        this.movieService = movieService;
    }

@GetMapping
public List<MovieDTO> buscar(@RequestParam String q) {
    return movieService.buscarOMDb(q);
}
}
