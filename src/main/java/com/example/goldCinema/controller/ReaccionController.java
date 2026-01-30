package com.example.goldCinema.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.goldCinema.model.Reaccion;
import com.example.goldCinema.service.ReaccionService;

@RestController
@RequestMapping("/reacciones")
public class ReaccionController {

    private final ReaccionService reaccionService;

    public ReaccionController(ReaccionService reaccionService) {
        this.reaccionService = reaccionService;
    }

    @PostMapping
    public void reaccionar(
            @RequestParam Long peliculaId,
            @RequestParam Reaccion.Tipo tipo) {

        reaccionService.reaccionar(peliculaId, tipo);
    }
}
