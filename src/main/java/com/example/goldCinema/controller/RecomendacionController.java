package com.example.goldCinema.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goldCinema.dto.MovieDTO;
import com.example.goldCinema.service.RecomendacionService;

@RestController
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

@GetMapping("/api/recomendaciones")
public List<MovieDTO> recomendaciones(Principal principal) {
    if (principal == null) {
        System.out.println("Ojo: Usuario no detectado por Spring Security");
        return List.of(); 
    }
    
    String username = principal.getName();
    System.out.println("Buscando recomendaciones para: " + username);
    
    List<MovieDTO> lista = recomendacionService.obtenerRecomendadas(username);
    System.out.println("Total encontrados: " + lista.size());
    
    return lista;
}
}

