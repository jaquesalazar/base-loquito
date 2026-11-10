package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario accederOCrearUsuario(String username) {
        String nombreUsuario = username.trim();

        return usuarioRepository.findByUsernameIgnoreCase(nombreUsuario)
                .orElseGet(() -> {
                    Usuario usuarioNuevo = new Usuario();
                    usuarioNuevo.setUsername(nombreUsuario);
                    usuarioNuevo.setSeguidores(0);
                    usuarioNuevo.setDinero(0);
                    usuarioNuevo.setCohesion(0);

                    return usuarioRepository.save(usuarioNuevo);
                });
    }

    @Transactional
    public Usuario crearUsuario(
            String username,
            Integer seguidores,
            Integer dinero,
            Integer cohesion) {

        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setUsername(username);
        usuarioNuevo.setSeguidores(seguidores);
        usuarioNuevo.setDinero(dinero);
        usuarioNuevo.setCohesion(cohesion);

        return usuarioRepository.save(usuarioNuevo);
    }
}
