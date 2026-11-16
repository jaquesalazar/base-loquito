package com.elloquito.baseloquito.service;

// Servicio para administrar los videos asignados a cada jugador.

import com.elloquito.baseloquito.model.JugadorVideo;
import com.elloquito.baseloquito.model.JugadorVideoId;
import com.elloquito.baseloquito.model.ProgresoVideo;
import com.elloquito.baseloquito.model.ProgresoVideoId;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.repository.JugadorVideoRepository;
import com.elloquito.baseloquito.repository.ProgresoVideoRepository;

import jakarta.persistence.EntityManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class JugadorVideoService {

    private final JugadorVideoRepository jugadorVideoRepository;
    private final ProgresoVideoRepository progresoVideoRepository;
    private final EntityManager entityManager;

    public JugadorVideoService(
            JugadorVideoRepository jugadorVideoRepository,
            ProgresoVideoRepository progresoVideoRepository,
            EntityManager entityManager) {

        this.jugadorVideoRepository = jugadorVideoRepository;
        this.progresoVideoRepository = progresoVideoRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public JugadorVideo asignarVideo(Integer idUsuario, Integer idVideo){

        Usuario usuario = entityManager.find(Usuario.class, idUsuario);
        Video video = entityManager.find(Video.class, idVideo);

        if (usuario == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no existe"
            );
        }

        if (video == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El video no existe"
            );
        }

        JugadorVideoId id = new JugadorVideoId(idUsuario, idVideo);

        JugadorVideo asignacion = jugadorVideoRepository.findById(id)
                .orElseGet(() -> {
                    JugadorVideo nueva = new JugadorVideo();
                    nueva.setId(id);
                    nueva.setUsuario(usuario);
                    nueva.setVideo(video);
                    return jugadorVideoRepository.save(nueva);
                });

        ProgresoVideoId progresoId = new ProgresoVideoId(idUsuario, idVideo);

        if (!progresoVideoRepository.existsById(progresoId)) {
            ProgresoVideo progreso = new ProgresoVideo();
            progreso.setId(progresoId);
            progreso.setUsuario(usuario);
            progreso.setVideo(video);
            progreso.setLiked(false);
            progreso.setVistas(0);
            progreso.setLikesAutomaticos(0);
            progreso.setUltimaActualizacion(
                    LocalDateTime.now(ZoneOffset.UTC)
            );
            progreso.setSeguidoresUltimaActualizacion(usuario.getSeguidores());

            progresoVideoRepository.save(progreso);
        }

        return asignacion;
    }

    @Transactional(readOnly = true)
    public List<JugadorVideo> obtenerVideosPorJugador(Integer idUsuario){

        Usuario usuario = entityManager.find(Usuario.class, idUsuario);

        if (usuario == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no existe"
            );
        }

        return jugadorVideoRepository
                .findByUsuario_IdOrderByVideo_IdVideoAsc(idUsuario);
    }
}
