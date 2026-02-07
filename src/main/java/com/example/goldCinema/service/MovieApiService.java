package com.example.goldCinema.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.goldCinema.dto.MovieDTO;

@Service
public class MovieApiService {

        @Value("${omdb.api.key}")
        private String apiKey;

        private final RestClient client = RestClient.builder()
                .baseUrl("https://www.omdbapi.com/")
                .build();

        public String buscarPorTitulo(String titulo) {
                return client.get()
                        .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", apiKey)
                        .queryParam("s", titulo)
                        .build())
                .retrieve()
                .body(String.class);
        }

        public MovieDTO buscarPorId(String imdbId) {

        Map<String, Object> map = client.get()
        .uri(uriBuilder -> uriBuilder
                .queryParam("apikey", apiKey)
                .queryParam("i", imdbId)
                .build())
        .retrieve()
        .body(Map.class);

        if (map == null || "False".equals(map.get("Response"))) {
        return null;
}

        MovieDTO dto = new MovieDTO();

        dto.setTitulo((String) map.get("Title"));
        dto.setDescripcion((String) map.get("Plot"));
        dto.setImagen((String) map.get("Poster"));
        dto.setGenero((String) map.get("Genre"));
        dto.setActores((String) map.get("Actors"));
        dto.setAnio((String) map.get("Year"));

        return dto;
}

}
