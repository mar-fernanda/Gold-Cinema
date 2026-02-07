package com.example.goldCinema.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.goldCinema.service.MovieService;

@Configuration
public class DataFixConfig {

    @Bean
    CommandLineRunner completarPeliculas(MovieService movieService) {
        return args -> {
            System.out.println("🔄 Completando películas desde OMDb...");
            movieService.completarPeliculasExistentes();
            System.out.println("🎉 Proceso terminado");
        };
    }
}
