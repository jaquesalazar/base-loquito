
package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.JugadorVideo;
import com.elloquito.baseloquito.model.ProgresoVideo;
import com.elloquito.baseloquito.model.ProgresoVideoId;
import com.elloquito.baseloquito.repository.JugadorVideoRepository;
import com.elloquito.baseloquito.repository.ProgresoVideoRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LikesAutomaticosService {

    private static final int LIKES_POR_MINUTO = 10;

    private final JugadorVideoRepository jugadorVideoRepository;
    private final ProgresoVideoRepository progresoVideoRepository;

    public LikesAutomaticosService(
            JugadorVideoRepository jugadorVideoRepository,
            ProgresoVideoRepository progresoVideoRepository) {
        this.jugadorVideoRepository = jugadorVideoRepository;
        this.progresoVideoRepository = progresoVideoRepository;
    }

    @Transactional
    public void generarLikesPorMinuto() {
        generarLikesPorMinuto(LocalDateTime.now());
    }

    @Transactional
    public void generarLikesPorMinuto(LocalDateTime momento) {
        List<JugadorVideo> asignaciones =
                jugadorVideoRepository.findAllByOrderByUsuario_IdAscVideo_IdVideoAsc();

        Map<Integer, List<JugadorVideo>> asignacionesPorJugador =
                asignaciones.stream().collect(Collectors.groupingBy(
                        asignacion -> asignacion.getUsuario().getId()
                ));

        int minutoActual = momento.getMinute();

        for (Map.Entry<Integer, List<JugadorVideo>> entrada
                : asignacionesPorJugador.entrySet()) {

            List<JugadorVideo> videos = entrada.getValue();
            int cantidadVideos = videos.size();

            int likesBase = LIKES_POR_MINUTO / cantidadVideos;
            int sobrantes = LIKES_POR_MINUTO % cantidadVideos;
            int inicioRotacion = minutoActual % cantidadVideos;

            for (int i = 0; i < cantidadVideos; i++) {
                JugadorVideo asignacion = videos.get(i);

                ProgresoVideoId id = new ProgresoVideoId(
                        entrada.getKey(),
                        asignacion.getVideo().getIdVideo()
                );

                ProgresoVideo progreso = progresoVideoRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException(
                                "No existe el progreso de un video asignado"
                        ));

                int likesExtra = 0;

                for (int j = 0; j < sobrantes; j++) {
                    if (i == (inicioRotacion + j) % cantidadVideos) {
                        likesExtra = 1;
                        break;
                    }
                }

                int likesActuales = progreso.getLikesAutomaticos() != null
                        ? progreso.getLikesAutomaticos()
                        : 0;

                progreso.setLikesAutomaticos(
                        likesActuales + likesBase + likesExtra
                );
            }
        }
    }
}
