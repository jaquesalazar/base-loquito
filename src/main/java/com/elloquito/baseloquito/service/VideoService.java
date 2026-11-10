package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.repository.VideoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
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
            String tag,
            String caption,
            String hashtags,
            String videoUrl,
            Integer comments,
            Integer shares) {

        Video videoNuevo = new Video();
        videoNuevo.setTitulo(titulo);
        videoNuevo.setVistas(vistas != null ? vistas : 0);
        videoNuevo.setLikes(likes != null ? likes : 0);
        videoNuevo.setTag(tag);
        videoNuevo.setCaption(caption);
        videoNuevo.setHashtags(hashtags);
        videoNuevo.setVideoUrl(videoUrl);
        videoNuevo.setComments(comments != null ? comments : 0);
        videoNuevo.setShares(shares != null ? shares : 0);

        return videoRepository.saveAndFlush(videoNuevo);
    }
}
