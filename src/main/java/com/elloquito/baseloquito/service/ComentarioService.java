package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.Comentario;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.repository.ComentarioRepository;
import com.elloquito.baseloquito.repository.UsuarioRepository;
import com.elloquito.baseloquito.repository.VideoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final VideoRepository videoRepository;

    public ComentarioService(
            ComentarioRepository comentarioRepository,
            UsuarioRepository usuarioRepository,
            VideoRepository videoRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.videoRepository = videoRepository;
    }

    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosPorVideo(
            Integer idUsuario,
            Integer idVideo) {

        return comentarioRepository
                .findByUsuario_IdAndVideo_IdVideoOrderByCreatedAtDesc(
                        idUsuario,
                        idVideo
                );
    }

    @Transactional(readOnly = true)
    public List<Comentario> obtenerComentariosPorUsuario(Integer idUsuario) {
        return comentarioRepository.findByUsuario_IdOrderByCreatedAtDesc(idUsuario);
    }

    @Transactional
    public Comentario crearComentario(Integer idUsuario, Integer idVideo, String texto) {
        if (texto == null || texto.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El comentario no puede estar vacío");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe"));

        Video video = videoRepository.findById(idVideo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El video no existe"));

        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setVideo(video);
        comentario.setTexto(texto.trim());

        return comentarioRepository.save(comentario);
    }

    @Transactional
    public void eliminarComentario(Integer idVideo, Integer idComentario, Integer idUsuario) {
        Comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El comentario no existe"));

        if (!comentario.getVideo().getIdVideo().equals(idVideo)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El comentario no pertenece a este video");
        }

        if (!comentario.getUsuario().getId().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes eliminar el comentario de otro jugador");
        }

        comentarioRepository.delete(comentario);
    }
}
