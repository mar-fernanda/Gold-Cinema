package com.example.goldCinema.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.goldCinema.dto.RegistroDTO;
import com.example.goldCinema.service.UsuarioService;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        model.addAttribute("registro", new RegistroDTO());
        return "registro";
    }

@PostMapping("/registro")
public String procesarRegistro(
        @Valid @ModelAttribute("registro") RegistroDTO registro,
        BindingResult result,
        Model model) {

    if (result.hasErrors()) {
        return "registro";
    }

    try {
        usuarioService.registrar(registro);
    } catch (IllegalStateException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "registro";
    }

    model.addAttribute("successMessage", "Registro exitoso. Ahora puedes iniciar sesión");
    return "login";
}
}