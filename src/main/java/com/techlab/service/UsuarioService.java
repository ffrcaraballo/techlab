package com.techlab.service;

import com.techlab.model.Usuario;
import com.techlab.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        // Validar si el email ya existe
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            throw new Exception("El email ya está registrado");
        }
        // Guardar usuario nuevo
        return usuarioRepository.save(usuario);
    }
}
