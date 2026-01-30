package com.example.goldCinema.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.goldCinema.dto.ComentarioDTO;
import com.example.goldCinema.model.Favorito;
import com.example.goldCinema.model.Pelicula;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.service.ComentarioService;
import com.example.goldCinema.service.FavoritoService;
import com.example.goldCinema.service.MovieService;
import com.example.goldCinema.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final MovieService movieService;
    private final ComentarioService comentarioService;
    private final FavoritoService favoritoService;

    public UsuarioController(
            UsuarioService usuarioService,
            MovieService movieService,
            ComentarioService comentarioService,
            FavoritoService favoritoService) {

        this.usuarioService = usuarioService;
        this.movieService = movieService;
        this.comentarioService = comentarioService;
        this.favoritoService = favoritoService;
    }

    @GetMapping("/favoritos")
public String favoritos(Model model, Principal principal) {

    Usuario usuario = obtenerUsuario(principal);
    if (usuario == null) {
        return "redirect:/login";
    }

    List<Favorito> favoritos = favoritoService.listar(usuario);

    List<Pelicula> porVer = movieService.peliculasPorVer(usuario);

    List<Pelicula> vistos = movieService.peliculasVistas(usuario);

    model.addAttribute("usuario", usuario);
    model.addAttribute("favoritos", favoritos);
    model.addAttribute("porVer", porVer);
    model.addAttribute("vistos", vistos);
    model.addAttribute("titulo", "Mi Perfil");

    return "perfil";
}


    private Usuario obtenerUsuario(Principal principal) {
        if (principal == null) return null;
        return usuarioService.buscarPorUsername(principal.getName());
    }
@PostMapping("/favorito/{movieId}")
public ResponseEntity<?> agregarFavorito(
        @PathVariable String movieId,
        @AuthenticationPrincipal UserDetails userDetails) {

    Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());
    favoritoService.agregar(usuario, movieId);
    return ResponseEntity.ok().build();
}


    @PostMapping("/porver")
    public ResponseEntity<?> agregarPorVer(
            @RequestParam String movieId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());
        movieService.agregarPorVer(usuario, movieId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vistos")
    public ResponseEntity<?> agregarVistos(
            @RequestParam String movieId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());
        movieService.marcarVisto(usuario, movieId);
        return ResponseEntity.ok().build();
    }
@GetMapping("/comentarios")
public ResponseEntity<List<ComentarioDTO>> obtenerComentarios(@RequestParam String movieId) {
    List<ComentarioDTO> dtos = comentarioService.obtenerComentariosPorImdb(movieId)
        .stream()
        .map(c -> {
            ComentarioDTO dto = new ComentarioDTO();
            dto.setId(c.getId());
            dto.setTexto(c.getTexto());
            dto.setFecha(c.getFecha() != null ? c.getFecha().toString() : "Fecha desconocida");
            dto.setUsername(c.getUsuario() != null ? c.getUsuario().getUsername() : "Anon");
            return dto;
        })
        .toList();

    return ResponseEntity.ok(dtos);
}

@PostMapping("/comentario")
public ResponseEntity<Void> agregarComentario(
        @RequestParam String movieId,
        @RequestParam String texto,
        @AuthenticationPrincipal UserDetails userDetails) {

    Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());
    comentarioService.agregarComentario(movieId, texto, usuario);
    return ResponseEntity.ok().build();
}

    @GetMapping("/votos")
    public ResponseEntity<Double> obtenerVotos(
            @RequestParam String movieId) {

        double porcentaje = movieService.obtenerPorcentajeLikes(movieId);
        return ResponseEntity.ok(porcentaje);
    }

    @PostMapping("/votar")
    public ResponseEntity<?> votar(
            @RequestParam String movieId,
            @RequestParam boolean like,
            @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());
        movieService.votar(movieId, like, usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estado")
public ResponseEntity<?> obtenerEstado(@RequestParam String movieId,
                                    @AuthenticationPrincipal UserDetails userDetails) {
    Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());

    boolean favorito = favoritoService.listar(usuario).stream()
                    .anyMatch(f -> f.getPelicula().getImdbId().equals(movieId));

    boolean porVer = usuario.getPorVer().stream()
                .anyMatch(p -> p.getImdbId().equals(movieId));

    boolean visto = usuario.getVistos().stream()
                .anyMatch(p -> p.getImdbId().equals(movieId));

    return ResponseEntity.ok(new EstadoResponse(favorito, porVer, visto));
}

public static class EstadoResponse {
    public boolean favorito;
    public boolean porVer;
    public boolean visto;

    public EstadoResponse(boolean favorito, boolean porVer, boolean visto) {
        this.favorito = favorito;
        this.porVer = porVer;
        this.visto = visto;
    }
}

    @PostMapping("/favorito/toggle/{movieId}")
public ResponseEntity<?> toggleFavorito(
        @PathVariable String movieId,
        @RequestParam String accion,
        @AuthenticationPrincipal UserDetails userDetails) {

    Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());

    if ("agregar".equals(accion)) {
        favoritoService.agregar(usuario, movieId);
    } else if ("eliminar".equals(accion)) {
        favoritoService.eliminar(usuario, movieId);
    }

    return ResponseEntity.ok().build();
}

@PostMapping("/porver/toggle")
public ResponseEntity<?> togglePorVer(
        @RequestParam String movieId,
        @RequestParam String accion,
        @AuthenticationPrincipal UserDetails userDetails) {

    Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());

    if ("agregar".equals(accion)) {
        movieService.agregarPorVer(usuario, movieId);
    } else if ("eliminar".equals(accion)) {
        usuario.getPorVer().removeIf(p -> p.getImdbId().equals(movieId));
        usuarioService.guardar(usuario);
    }

    return ResponseEntity.ok().build();
}

@PostMapping("/vistos/toggle")
public ResponseEntity<?> toggleVistos(
        @RequestParam String movieId,
        @AuthenticationPrincipal UserDetails userDetails) {

    Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());
    movieService.marcarVisto(usuario, movieId);

    usuario.getPorVer().removeIf(p -> p.getImdbId().equals(movieId));
    usuarioService.guardar(usuario);

    return ResponseEntity.ok().build();
}

}
