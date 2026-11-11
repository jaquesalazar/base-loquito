package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.HistorialEstadistica;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.repository.HistorialEstadisticaRepository;
import com.elloquito.baseloquito.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final HistorialEstadisticaRepository historialRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            HistorialEstadisticaRepository historialRepository) {
        this.usuarioRepository = usuarioRepository;
        this.historialRepository = historialRepository;
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

    @Transactional
    public Usuario actualizarEstadisticas(
            Integer idUsuario,
            Integer seguidores,
            Integer dinero,
            Integer cohesion) {

        if (cohesion < 0 || cohesion > 100) {
            throw new IllegalArgumentException("La cohesión debe estar entre 0 y 100.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado."));

        usuario.setSeguidores(seguidores);
        usuario.setDinero(dinero);
        usuario.setCohesion(cohesion);

        LocalDate fechaActual = LocalDate.now();

        HistorialEstadistica historial = historialRepository
                .findByUsuarioIdAndFecha(idUsuario, fechaActual)
                .orElseGet(HistorialEstadistica::new);

        historial.setUsuario(usuario);
        historial.setFecha(fechaActual);
        historial.setSeguidores(seguidores);
        historial.setDinero(dinero);
        historial.setCohesion(cohesion);

        historialRepository.save(historial);

        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<HistorialEstadistica> obtenerHistorialEstadisticas(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new IllegalArgumentException("Jugador no encontrado.");
        }

        return historialRepository.findByUsuarioIdOrderByFechaAsc(idUsuario);
    }
}
