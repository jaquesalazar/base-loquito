package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.Video;
import com.elloquito.baseloquito.service.VideoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    public record VideoRequest(
            @Size(max = 150) String titulo,
            @PositiveOrZero Integer vistas,
            @PositiveOrZero Integer likes,
            @NotNull Integer idUsuario,
            @Size(max = 30) String tag,
            @NotBlank String caption,
            @Size(max = 200) String hashtags,
            @NotBlank String videoUrl,
            @PositiveOrZero Integer comments,
            @PositiveOrZero Integer shares
    ) {}

    public record VideoResponse(
            Integer idVideo,
            Integer likes,
            String titulo,
            Integer vistas,
            Integer idUsuario,
            String tag,
            String caption,
            String hashtags,
            String videoUrl,
            Integer comments,
            Integer shares,
            LocalDateTime createdAt
    ) {}

    @GetMapping
    public List<VideoResponse> obtenerVideos() {
        return videoService.obtenerVideos()
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    @PostMapping
    public ResponseEntity<VideoResponse> crearVideo(
            @Valid @RequestBody VideoRequest datos) {

        Video guardado = videoService.crearVideo(
                datos.titulo(),
                datos.vistas(),
                datos.likes(),
                datos.idUsuario(),
                datos.tag(),
                datos.caption(),
                datos.hashtags(),
                datos.videoUrl(),
                datos.comments(),
                datos.shares()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertirRespuesta(guardado));
    }

    private VideoResponse convertirRespuesta(Video video) {
        return new VideoResponse(
                video.getIdVideo(),
                video.getLikes(),
                video.getTitulo(),
                video.getVistas(),
                video.getUsuario().getId(),
                video.getTag(),
                video.getCaption(),
                video.getHashtags(),
                video.getVideoUrl(),
                video.getComments(),
                video.getShares(),
                video.getCreatedAt()
        );
    }
}
