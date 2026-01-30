package com.example.goldCinema.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.goldCinema.dto.MovieDTO;
import com.example.goldCinema.model.Favorito;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.service.FavoritoService;
import com.example.goldCinema.service.MovieService;
import com.example.goldCinema.service.UsuarioService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private final UsuarioService usuarioService;
    private final FavoritoService favoritoService;
    private final MovieService movieService;

    public PerfilController(UsuarioService usuarioService,
                            FavoritoService favoritoService,
                            MovieService movieService) {
        this.usuarioService = usuarioService;
        this.favoritoService = favoritoService;
        this.movieService = movieService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public String mostrarPerfil(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        Usuario usuario = usuarioService.buscarPorUsername(principal.getName());
        if (usuario == null) return "redirect:/login";

        List<com.example.goldCinema.dto.MovieDTO> favoritosDTO = favoritoService.listar(usuario).stream()
                .map(f -> movieService.buscarOMDbPorId(f.getPelicula().getImdbId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<com.example.goldCinema.dto.MovieDTO> porVerDTO = usuario.getPorVer() != null
                ? usuario.getPorVer().stream()
                    .map(p -> movieService.buscarOMDbPorId(p.getImdbId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
                : new ArrayList<>();

        List<com.example.goldCinema.dto.MovieDTO> vistosDTO = usuario.getVistos() != null
                ? usuario.getVistos().stream()
                    .map(p -> movieService.buscarOMDbPorId(p.getImdbId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
                : new ArrayList<>();

        Map<String, Long> generosFavoritos = obtenerGenerosFavoritos(favoritosDTO);

        model.addAttribute("usuario", usuario);
        model.addAttribute("favoritos", favoritosDTO);
        model.addAttribute("porVer", porVerDTO);
        model.addAttribute("vistos", vistosDTO);
        model.addAttribute("generosFavoritos", generosFavoritos);
        model.addAttribute("titulo", "Mi Perfil");

        return "perfil";
    }

private Map<String, Long> obtenerGenerosFavoritos(List<com.example.goldCinema.dto.MovieDTO> favoritosDTO) {
    if (favoritosDTO == null) return Map.of();

    return favoritosDTO.stream()
            .filter(p -> p.getGenero() != null)
            .flatMap(p -> Arrays.stream(p.getGenero().split(",\\s*")))
            .collect(Collectors.groupingBy(g -> g, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
}
@GetMapping("/section")
public String perfilSection(Model model, Principal principal) {
    Usuario usuario = usuarioService.buscarPorUsername(principal.getName());

    List<Favorito> favoritos = favoritoService.listar(usuario);
    List<MovieDTO> favoritosDTO = favoritos.stream()
        .map(f -> movieService.buscarOMDbPorId(f.getPelicula().getImdbId()))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    List<MovieDTO> porVerDTO = usuario.getPorVer().stream()
        .map(p -> movieService.buscarOMDbPorId(p.getImdbId()))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    List<MovieDTO> vistosDTO = usuario.getVistos().stream()
        .map(p -> movieService.buscarOMDbPorId(p.getImdbId()))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    Map<String, Long> generosFavoritos = obtenerGenerosFavoritos(favoritosDTO);

    model.addAttribute("favoritos", favoritosDTO);
    model.addAttribute("porVer", porVerDTO);
    model.addAttribute("vistos", vistosDTO);
    model.addAttribute("generosFavoritos", generosFavoritos);

    return "perfil :: perfil-seccion";
}
    }
