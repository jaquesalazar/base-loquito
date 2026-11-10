package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.ProgresoVideo;
import com.elloquito.baseloquito.model.ProgresoVideoId;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.repository.JugadorVideoRepository;
import com.elloquito.baseloquito.repository.ProgresoVideoRepository;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ProgresoVideoServiceTest {

    @Mock
    private ProgresoVideoRepository progresoVideoRepository;

    @Mock
    private JugadorVideoRepository jugadorVideoRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ProgresoVideoService progresoVideoService;

    @Test
    void registrarLikeDebeActualizarLikeIndividualSinModificarContadorGlobal() {
        Usuario usuario = crearUsuario();
        Video video = crearVideo();
        ProgresoVideo progreso = crearProgreso(usuario, video);

        prepararAsignacion(usuario, video, progreso);

        ProgresoVideo resultado = progresoVideoService.registrarLike(1, 1, true);

        assertThat(resultado.getLiked()).isTrue();
        assertThat(video.getLikes()).isEqualTo(10);
        verify(progresoVideoRepository).save(progreso);
    }

    @Test
    void registrarLikeNoDebeDuplicarLikes() {
        Usuario usuario = crearUsuario();
        Video video = crearVideo();
        ProgresoVideo progreso = crearProgreso(usuario, video);
        progreso.setLiked(true);

        prepararAsignacion(usuario, video, progreso);

        ProgresoVideo resultado = progresoVideoService.registrarLike(1, 1, true);

        assertThat(resultado.getLiked()).isTrue();
        assertThat(video.getLikes()).isEqualTo(10);
    }

    @Test
    void aumentarVistasDebeActualizarProgresoIndividualSinModificarContadorGlobal() {
        Usuario usuario = crearUsuario();
        Video video = crearVideo();
        ProgresoVideo progreso = crearProgreso(usuario, video);

        prepararAsignacion(usuario, video, progreso);

        ProgresoVideo resultado = progresoVideoService.aumentarVistas(1, 1, 5);

        assertThat(resultado.getVistas()).isEqualTo(5);
        assertThat(video.getVistas()).isEqualTo(100);
    }

    @Test
    void registrarLikeDebeRechazarVideosNoAsignadosAlJugador() {
        Usuario usuario = crearUsuario();
        Video video = crearVideo();

        given(entityManager.find(Usuario.class, 1)).willReturn(usuario);
        given(entityManager.find(Video.class, 1)).willReturn(video);
        given(jugadorVideoRepository.existsByUsuario_IdAndVideo_IdVideo(1, 1))
                .willReturn(false);

        assertThatThrownBy(() -> progresoVideoService.registrarLike(1, 1, true))
                .isInstanceOf(org.springframework.web.server.ResponseStatusException.class)
                .hasMessageContaining("El video no está asignado");

        verify(progresoVideoRepository, never()).save(any(ProgresoVideo.class));
    }

    private void prepararAsignacion(
            Usuario usuario,
            Video video,
            ProgresoVideo progreso) {

        given(entityManager.find(Usuario.class, 1)).willReturn(usuario);
        given(entityManager.find(Video.class, 1)).willReturn(video);
        given(jugadorVideoRepository.existsByUsuario_IdAndVideo_IdVideo(1, 1))
                .willReturn(true);
        given(progresoVideoRepository.findById(any(ProgresoVideoId.class)))
                .willReturn(Optional.of(progreso));
        given(progresoVideoRepository.save(any(ProgresoVideo.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
    }

    private Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setUsername("jugador1");
        return usuario;
    }

    private Video crearVideo() {
        Video video = new Video();
        video.setLikes(10);
        video.setVistas(100);
        return video;
    }

    private ProgresoVideo crearProgreso(Usuario usuario, Video video) {
        ProgresoVideo progreso = new ProgresoVideo();
        progreso.setId(new ProgresoVideoId(1, 1));
        progreso.setUsuario(usuario);
        progreso.setVideo(video);
        progreso.setLiked(false);
        progreso.setVistas(0);
        return progreso;
    }
}
