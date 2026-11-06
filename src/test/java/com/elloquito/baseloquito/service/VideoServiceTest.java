package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.repository.VideoRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private VideoService videoService;

    @Test
    void obtenerVideosDebeDevolverLaListaDeVideos() {
        Video video = new Video();
        video.setTitulo("Mi primer video");
        video.setLikes(100);
        video.setVistas(500);

        given(videoRepository.findAll()).willReturn(List.of(video));

        List<Video> resultado = videoService.obtenerVideos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTitulo()).isEqualTo("Mi primer video");
        assertThat(resultado.get(0).getLikes()).isEqualTo(100);
        verify(videoRepository).findAll();
    }

    @Test
    void crearVideoDebeGuardarYDevolverElVideo() {
        Usuario usuario = new Usuario();
        usuario.setUsername("el_loquito");

        given(entityManager.find(Usuario.class, 1)).willReturn(usuario);
        given(videoRepository.saveAndFlush(any(Video.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Video resultado = videoService.crearVideo(
                "Mi primer video",
                500,
                100,
                1,
                "Entretenimiento",
                "Mi primer video en CatTube",
                "#CatTube",
                "https://ejemplo.com/video.mp4",
                10,
                5
        );

        assertThat(resultado.getTitulo()).isEqualTo("Mi primer video");
        assertThat(resultado.getVistas()).isEqualTo(500);
        assertThat(resultado.getLikes()).isEqualTo(100);
        assertThat(resultado.getUsuario()).isSameAs(usuario);
        assertThat(resultado.getTag()).isEqualTo("Entretenimiento");
        assertThat(resultado.getCaption()).isEqualTo("Mi primer video en CatTube");
        assertThat(resultado.getHashtags()).isEqualTo("#CatTube");
        assertThat(resultado.getVideoUrl()).isEqualTo("https://ejemplo.com/video.mp4");
        assertThat(resultado.getComments()).isEqualTo(10);
        assertThat(resultado.getShares()).isEqualTo(5);

        verify(entityManager).find(Usuario.class, 1);
        verify(videoRepository).saveAndFlush(any(Video.class));
        verify(entityManager).refresh(resultado);
    }
}
