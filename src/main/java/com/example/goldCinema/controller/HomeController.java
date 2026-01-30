package com.example.goldCinema.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.service.UsuarioService;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {

        model.addAttribute("titulo", "GoldCinema");

        if (principal != null) {
            Usuario usuario = usuarioService.buscarPorUsername(principal.getName());
            model.addAttribute("usuario", usuario);
        }

        return "index";
    }
}
