package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.Comentario;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.repository.ComentarioRepository;
import com.elloquito.baseloquito.repository.UsuarioRepository;
import com.elloquito.baseloquito.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private ComentarioService comentarioService;

    @Test
    void obtenerComentariosDebeFiltrarPorJugadorYVideo() {
        given(comentarioRepository
                .findByUsuario_IdAndVideo_IdVideoOrderByCreatedAtDesc(1, 1))
                .willReturn(List.of());

        List<Comentario> resultado =
                comentarioService.obtenerComentariosPorVideo(1, 1);

        assertThat(resultado).isEmpty();
        verify(comentarioRepository)
                .findByUsuario_IdAndVideo_IdVideoOrderByCreatedAtDesc(1, 1);
    }

    @Test
    void crearComentarioNoDebeModificarContadorGlobal() {
        Usuario usuario = crearUsuario(1);
        Video video = crearVideo();

        given(usuarioRepository.findById(1)).willReturn(Optional.of(usuario));
        given(videoRepository.findById(1)).willReturn(Optional.of(video));
        given(comentarioRepository.save(any(Comentario.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Comentario resultado =
                comentarioService.crearComentario(1, 1, "Mi comentario");

        assertThat(resultado.getTexto()).isEqualTo("Mi comentario");
        assertThat(resultado.getUsuario()).isEqualTo(usuario);
        assertThat(resultado.getVideo()).isEqualTo(video);
        assertThat(video.getComments()).isEqualTo(10);
        verify(videoRepository, never()).save(any(Video.class));
    }

    @Test
    void eliminarComentarioPropioDebeEliminarseSinModificarContadorGlobal() {
        Usuario usuario = crearUsuario(1);
        Video video = crearVideo();
        Comentario comentario = crearComentario(usuario, video);

        given(comentarioRepository.findById(1))
                .willReturn(Optional.of(comentario));

        comentarioService.eliminarComentario(1, 1, 1);

        verify(comentarioRepository).delete(comentario);
        assertThat(video.getComments()).isEqualTo(10);
        verify(videoRepository, never()).save(any(Video.class));
    }

    @Test
    void eliminarComentarioDeOtroJugadorDebeRechazarse() {
        Usuario propietario = crearUsuario(2);
        Video video = crearVideo();
        Comentario comentario = crearComentario(propietario, video);

        given(comentarioRepository.findById(1))
                .willReturn(Optional.of(comentario));

        assertThatThrownBy(() ->
                comentarioService.eliminarComentario(1, 1, 1))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("No puedes eliminar");

        verify(comentarioRepository, never()).delete(any(Comentario.class));
    }

    private Usuario crearUsuario(Integer id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setUsername("jugador" + id);
        return usuario;
    }

    private Video crearVideo() {
    Video video = new Video();
    video.setIdVideo(1);
    video.setLikes(10);
    video.setVistas(100);
    video.setComments(10);
    return video;
    }

    private Comentario crearComentario(Usuario usuario, Video video) {
        Comentario comentario = new Comentario();
        comentario.setIdComentario(1);
        comentario.setUsuario(usuario);
        comentario.setVideo(video);
        comentario.setTexto("Mi comentario");
        return comentario;
    }
}
