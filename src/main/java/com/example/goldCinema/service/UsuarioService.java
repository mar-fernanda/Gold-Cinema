package com.example.goldCinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.goldCinema.dto.RegistroDTO;
import com.example.goldCinema.model.Usuario;
import com.example.goldCinema.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrar(RegistroDTO registro) {

        if (!registro.getPassword().equals(registro.getConfirmPassword())) {
            throw new IllegalStateException("Las contraseñas no coinciden");
        }

        if (existeUsuario(registro.getUsername())) {
            throw new IllegalStateException("El usuario ya existe");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registro.getUsername());
        usuario.setEmail(registro.getEmail());
        usuario.setPassword(passwordEncoder.encode(registro.getPassword()));

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
    }
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public Usuario obtenerUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return buscarPorUsername(auth.getName());
    }
    public boolean existeUsuario(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }
}
