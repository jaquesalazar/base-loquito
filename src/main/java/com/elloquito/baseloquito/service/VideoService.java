package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.repository.VideoRepository;

import jakarta.persistence.EntityManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final EntityManager entityManager;

    public VideoService(
            VideoRepository videoRepository,
            EntityManager entityManager) {

        this.videoRepository = videoRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Video> obtenerVideos() {
        return videoRepository.findAll();
    }

    @Transactional
    public Video crearVideo(
            String titulo,
            Integer vistas,
            Integer likes,
            Integer idUsuario,
            String tag,
            String caption,
            String hashtags,
            String videoUrl,
            Integer comments,
            Integer shares) {

        Usuario usuario = entityManager.find(
                Usuario.class,
                idUsuario
        );

        if (usuario == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no existe"
            );
        }

        Video videoNuevo = new Video();
        videoNuevo.setTitulo(titulo);
        videoNuevo.setVistas(vistas);
        videoNuevo.setLikes(likes != null ? likes : 0);
        videoNuevo.setUsuario(usuario);
        videoNuevo.setTag(tag);
        videoNuevo.setCaption(caption);
        videoNuevo.setHashtags(hashtags);
        videoNuevo.setVideoUrl(videoUrl);
        videoNuevo.setComments(comments != null ? comments : 0);
        videoNuevo.setShares(shares != null ? shares : 0);

        Video videoGuardado = videoRepository.saveAndFlush(videoNuevo);

        entityManager.refresh(videoGuardado);

        return videoGuardado;
    }
}
