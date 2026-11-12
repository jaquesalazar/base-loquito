package com.elloquito.baseloquito.service;

// Servicio para generar el crecimiento automático del progreso de cada video.

import com.elloquito.baseloquito.model.JugadorVideo;
import com.elloquito.baseloquito.model.ProgresoVideo;
import com.elloquito.baseloquito.model.ProgresoVideoId;
import com.elloquito.baseloquito.repository.JugadorVideoRepository;
import com.elloquito.baseloquito.repository.ProgresoVideoRepository;

import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class LikesAutomaticosService {

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

        for (JugadorVideo asignacion : asignaciones) {

            ProgresoVideoId id = new ProgresoVideoId(
                    asignacion.getUsuario().getId(),
                    asignacion.getVideo().getIdVideo()
            );

            ProgresoVideo progreso = progresoVideoRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(
                            "No existe el progreso de un video asignado"
                    ));

            int seguidoresActuales = asignacion.getUsuario().getSeguidores() != null
                    ? asignacion.getUsuario().getSeguidores()
                    : 0;

            LocalDateTime ultimaActualizacion = progreso.getUltimaActualizacion();

            if (ultimaActualizacion == null) {
                progreso.setUltimaActualizacion(momento);
                progreso.setSeguidoresUltimaActualizacion(seguidoresActuales);
                continue;
            }

            long minutosPendientes = Duration.between(
                    ultimaActualizacion,
                    momento
            ).toMinutes();

            if (minutosPendientes <= 0) {
                continue;
            }

            int seguidoresRegistrados =
                    progreso.getSeguidoresUltimaActualizacion() != null
                            ? progreso.getSeguidoresUltimaActualizacion()
                            : 0;

            if (seguidoresRegistrados > 0 && seguidoresActuales > 0) {

                int likesActuales = progreso.getLikesAutomaticos() != null
                        ? progreso.getLikesAutomaticos()
                        : 0;

                int vistasActuales = progreso.getVistas() != null
                        ? progreso.getVistas()
                        : 0;

                for (long minuto = 0; minuto < minutosPendientes; minuto++) {

                    double multiplicadorLikes =
                            ThreadLocalRandom.current().nextDouble(0.1, 0.9);

                    double multiplicadorVistas =
                            ThreadLocalRandom.current().nextDouble(1.0, 5.0);

                    int incrementoLikes = redondearMultiploDeCinco(
                            seguidoresRegistrados * multiplicadorLikes
                    );

                    int incrementoVistas = redondearMultiploDeCinco(
                            seguidoresRegistrados * multiplicadorVistas
                    );

                    likesActuales += incrementoLikes;
                    vistasActuales += incrementoVistas;
                }

                progreso.setLikesAutomaticos(likesActuales);
                progreso.setVistas(vistasActuales);
            }

            progreso.setUltimaActualizacion(
                    ultimaActualizacion.plusMinutes(minutosPendientes)
            );

            progreso.setSeguidoresUltimaActualizacion(seguidoresActuales);
        }
    }

    private int redondearMultiploDeCinco(double cantidad) {
        return (int) (Math.round(cantidad / 5.0) * 5);
    }
}
