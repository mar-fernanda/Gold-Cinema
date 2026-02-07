package com.example.goldCinema.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.example.goldCinema.dto.MovieDTO;

@Component
public class MovieMapper {

    public List<MovieDTO> fromOmdbSearch(String json) {

        JSONObject obj = new JSONObject(json);

        if (!obj.has("Search")) return List.of();

        JSONArray array = obj.getJSONArray("Search");

        List<MovieDTO> lista = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject p = array.getJSONObject(i);

            MovieDTO dto = new MovieDTO();
            dto.setId(p.getString("imdbID"));
            dto.setTitulo(p.getString("Title"));
            dto.setImagen(p.optString("Poster"));
            dto.setAnio(p.optString("Year"));

            lista.add(dto);
        }

        return lista;
    }
}
