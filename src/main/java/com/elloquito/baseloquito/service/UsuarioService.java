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
    public Usuario crearUsuario(
            Integer seguidores,
            Integer dinero,
            Integer influencia,
            Integer cohesion) {

        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setSeguidores(seguidores);
        usuarioNuevo.setDinero(dinero);
        usuarioNuevo.setInfluencia(influencia);
        usuarioNuevo.setCohesion(cohesion);

        return usuarioRepository.save(usuarioNuevo);
    }
}
