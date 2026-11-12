package com.elloquito.baseloquito.service;

// Pruebas del crecimiento automático de likes y vistas por jugador y video.

import com.elloquito.baseloquito.model.JugadorVideo;
import com.elloquito.baseloquito.model.ProgresoVideo;
import com.elloquito.baseloquito.model.ProgresoVideoId;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.repository.JugadorVideoRepository;
import com.elloquito.baseloquito.repository.ProgresoVideoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LikesAutomaticosServiceTest {

    @Mock
    private JugadorVideoRepository jugadorVideoRepository;

    @Mock
    private ProgresoVideoRepository progresoVideoRepository;

    private LikesAutomaticosService likesAutomaticosService;
    private Map<ProgresoVideoId, ProgresoVideo> progresos;
    private List<JugadorVideo> asignaciones;

    @BeforeEach
    void preparar() {
        likesAutomaticosService = new LikesAutomaticosService(
                jugadorVideoRepository,
                progresoVideoRepository
        );

        progresos = new HashMap<>();
        asignaciones = new ArrayList<>();

        given(jugadorVideoRepository.findAllByOrderByUsuario_IdAscVideo_IdVideoAsc())
                .willReturn(asignaciones);

        org.mockito.Mockito.lenient()
                .when(progresoVideoRepository.findById(any(ProgresoVideoId.class)))
                .thenAnswer(invocation -> Optional.ofNullable(
                        progresos.get(invocation.getArgument(0))
                ));
    }

    @Test
    void cadaVideoDebeGenerarLikesYVistasIndependientemente() {
        prepararJugador(1, 100, 1, 2);

        ejecutar(0);

        for (Integer idVideo : List.of(1, 2)) {
            int likes = obtenerLikes(1, idVideo);
            int vistas = obtenerVistas(1, idVideo);

            assertThat(likes).isBetween(10, 90);
            assertThat(likes % 5).isZero();

            assertThat(vistas).isBetween(100, 500);
            assertThat(vistas % 5).isZero();
        }
    }

    @Test
    void debeAcumularElCrecimientoDeLosMinutosPendientes() {
        prepararJugador(1, 100, 1);

        ProgresoVideo progreso = obtenerProgreso(1, 1);
        progreso.setUltimaActualizacion(
                LocalDateTime.of(2026, 11, 9, 0, 58)
        );

        ejecutar(0);

        int likes = obtenerLikes(1, 1);
        int vistas = obtenerVistas(1, 1);

        assertThat(likes).isBetween(20, 180);
        assertThat(likes % 5).isZero();

        assertThat(vistas).isBetween(200, 1000);
        assertThat(vistas % 5).isZero();
    }

    @Test
    void cadaJugadorDebeTenerSuPropioCrecimiento() {
        prepararJugador(1, 100, 1);
        prepararJugador(2, 200, 1);

        ejecutar(0);

        assertThat(obtenerLikes(1, 1)).isBetween(10, 90);
        assertThat(obtenerVistas(1, 1)).isBetween(100, 500);

        assertThat(obtenerLikes(2, 1)).isBetween(20, 180);
        assertThat(obtenerVistas(2, 1)).isBetween(200, 1000);
    }

    @Test
    void jugadorSinSeguidoresNoDebeGenerarCrecimiento() {
        prepararJugador(1, 0, 1, 2);

        ejecutar(0);

        assertThat(obtenerLikes(1, 1)).isZero();
        assertThat(obtenerVistas(1, 1)).isZero();
        assertThat(obtenerLikes(1, 2)).isZero();
        assertThat(obtenerVistas(1, 2)).isZero();
    }

    @Test
    void jugadorSinVideosNoDebeRecibirCrecimiento() {
        ejecutar(0);

        assertThat(progresos).isEmpty();
    }

    private void prepararJugador(
            Integer idUsuario,
            int seguidores,
            Integer... idsVideos) {

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setSeguidores(seguidores);

        for (Integer idVideo : idsVideos) {
            Video video = new Video();
            video.setIdVideo(idVideo);

            JugadorVideo asignacion = new JugadorVideo();
            asignacion.setUsuario(usuario);
            asignacion.setVideo(video);
            asignaciones.add(asignacion);

            ProgresoVideoId id = new ProgresoVideoId(idUsuario, idVideo);

            ProgresoVideo progreso = new ProgresoVideo();
            progreso.setId(id);
            progreso.setUsuario(usuario);
            progreso.setVideo(video);
            progreso.setLiked(false);
            progreso.setVistas(0);
            progreso.setLikesAutomaticos(0);
            progreso.setUltimaActualizacion(
                    LocalDateTime.of(2026, 11, 9, 0, 59)
            );
            progreso.setSeguidoresUltimaActualizacion(seguidores);

            progresos.put(id, progreso);
        }
    }

    private void ejecutar(int minuto) {
        likesAutomaticosService.generarLikesPorMinuto(
                LocalDateTime.of(2026, 11, 9, 1, minuto)
        );
    }

    private ProgresoVideo obtenerProgreso(
            Integer idUsuario,
            Integer idVideo) {

        return progresos.get(new ProgresoVideoId(idUsuario, idVideo));
    }

    private int obtenerLikes(Integer idUsuario, Integer idVideo) {
        return obtenerProgreso(idUsuario, idVideo).getLikesAutomaticos();
    }

    private int obtenerVistas(Integer idUsuario, Integer idVideo) {
        return obtenerProgreso(idUsuario, idVideo).getVistas();
    }
}
