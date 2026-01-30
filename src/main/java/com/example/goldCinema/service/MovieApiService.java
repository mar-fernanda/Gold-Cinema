package com.example.goldCinema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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

        public String buscarPorId(String imdbId) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", apiKey)
                        .queryParam("i", imdbId)
                        .build())
                .retrieve()
                .body(String.class);
        }
}
