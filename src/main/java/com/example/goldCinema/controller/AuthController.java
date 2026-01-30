package com.example.goldCinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.goldCinema.dto.LoginDTO;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("login", new LoginDTO());
        return "login";
    }
}
