package com.elloquito.baseloquito.service;

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
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

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
    void debeGenerarDiezLikesParaUnVideo() {
        prepararJugador(1, 1);

        ejecutar(0);

        assertThat(obtenerLikes(1, 1)).isEqualTo(10);
    }

    @Test
    void debeRepartirDiezLikesEntreDosVideos() {
        prepararJugador(1, 1, 2);

        ejecutar(0);

        assertThat(obtenerLikes(1, 1)).isEqualTo(5);
        assertThat(obtenerLikes(1, 2)).isEqualTo(5);
        assertThat(totalJugador(1)).isEqualTo(10);
    }

    @Test
    void debeRotarLosLikesSobrantesEntreTresVideos() {
        prepararJugador(1, 1, 2, 3);

        ejecutar(0);

        assertThat(obtenerLikes(1, 1)).isEqualTo(4);
        assertThat(obtenerLikes(1, 2)).isEqualTo(3);
        assertThat(obtenerLikes(1, 3)).isEqualTo(3);
        assertThat(totalJugador(1)).isEqualTo(10);

        ejecutar(1);

        assertThat(obtenerLikes(1, 1)).isEqualTo(7);
        assertThat(obtenerLikes(1, 2)).isEqualTo(7);
        assertThat(obtenerLikes(1, 3)).isEqualTo(6);
        assertThat(totalJugador(1)).isEqualTo(20);
    }

    @Test
    void cadaJugadorDebeRecibirDiezLikesIndependientemente() {
        prepararJugador(1, 1, 2, 3);
        prepararJugador(2, 1, 2);

        ejecutar(0);

        assertThat(totalJugador(1)).isEqualTo(10);
        assertThat(totalJugador(2)).isEqualTo(10);
        assertThat(obtenerLikes(1, 1)).isEqualTo(4);
        assertThat(obtenerLikes(2, 1)).isEqualTo(5);
    }

    @Test
    void jugadorSinVideosNoDebeRecibirLikes() {
        ejecutar(0);

        assertThat(progresos).isEmpty();
    }

    private void prepararJugador(Integer idUsuario, Integer... idsVideos) {
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

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

            progresos.put(id, progreso);
        }
    }

    private void ejecutar(int minuto) {
        likesAutomaticosService.generarLikesPorMinuto(
                LocalDateTime.of(2026, 11, 9, 1, minuto)
        );
    }

    private int obtenerLikes(Integer idUsuario, Integer idVideo) {
        return progresos.get(new ProgresoVideoId(idUsuario, idVideo))
                .getLikesAutomaticos();
    }

    private int totalJugador(Integer idUsuario) {
        return progresos.entrySet().stream()
                .filter(entrada -> entrada.getKey().getIdUsuario().equals(idUsuario))
                .mapToInt(entrada -> entrada.getValue().getLikesAutomaticos())
                .sum();
    }
}
