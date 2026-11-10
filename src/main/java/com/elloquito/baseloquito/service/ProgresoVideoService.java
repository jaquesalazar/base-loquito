// Servicio encargado de administrar los likes y las vistas individuales de cada jugador.

package com.elloquito.baseloquito.service;

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

@Service
public class ProgresoVideoService {

    private final ProgresoVideoRepository progresoVideoRepository;
    private final JugadorVideoRepository jugadorVideoRepository;
    private final EntityManager entityManager;

    public ProgresoVideoService(
            ProgresoVideoRepository progresoVideoRepository,
            JugadorVideoRepository jugadorVideoRepository,
            EntityManager entityManager) {

        this.progresoVideoRepository = progresoVideoRepository;
        this.jugadorVideoRepository = jugadorVideoRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public ProgresoVideo registrarLike(
            Integer idUsuario,
            Integer idVideo,
            boolean liked) {

        validarAsignacion(idUsuario, idVideo);

        ProgresoVideo progreso = obtenerProgresoAsignado(idUsuario, idVideo);
        progreso.setLiked(liked);

        return progresoVideoRepository.save(progreso);
    }

    @Transactional
    public ProgresoVideo aumentarVistas(
            Integer idUsuario,
            Integer idVideo,
            Integer cantidad) {

        if (cantidad == null || cantidad <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad de vistas debe ser mayor que cero"
            );
        }

        validarAsignacion(idUsuario, idVideo);

        ProgresoVideo progreso = obtenerProgresoAsignado(idUsuario, idVideo);

        int vistasIndividuales = progreso.getVistas() != null
                ? progreso.getVistas()
                : 0;

        progreso.setVistas(vistasIndividuales + cantidad);

        return progresoVideoRepository.save(progreso);
    }

    @Transactional(readOnly = true)
    public ProgresoVideo obtenerProgreso(
            Integer idUsuario,
            Integer idVideo) {

        validarAsignacion(idUsuario, idVideo);

        return progresoVideoRepository
                .findByIdIdUsuarioAndIdIdVideo(idUsuario, idVideo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe progreso para este usuario y video"
                ));
    }

    private void validarAsignacion(Integer idUsuario, Integer idVideo) {

        Usuario usuario = entityManager.find(Usuario.class, idUsuario);

        if (usuario == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no existe"
            );
        }

        Video video = entityManager.find(Video.class, idVideo);

        if (video == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El video no existe"
            );
        }

        if (!jugadorVideoRepository.existsByUsuario_IdAndVideo_IdVideo(
                idUsuario,
                idVideo)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El video no está asignado a este jugador"
            );
        }
    }

    private ProgresoVideo obtenerProgresoAsignado(
            Integer idUsuario,
            Integer idVideo) {

        ProgresoVideoId id = new ProgresoVideoId(idUsuario, idVideo);

        return progresoVideoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe progreso para este usuario y video"
                ));
    }
}
