package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.ProgresoVideo;
import com.elloquito.baseloquito.service.ProgresoVideoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/{idUsuario}/videos/{idVideo}/progreso")
public class ProgresoVideoController {

    private final ProgresoVideoService progresoVideoService;

    public ProgresoVideoController(
            ProgresoVideoService progresoVideoService) {
        this.progresoVideoService = progresoVideoService;
    }

    public record LikeRequest(
            @NotNull Boolean liked
    ) {}

    public record VistasRequest(
        
            @NotNull @Positive Integer cantidad
    ) {}

    public record ProgresoVideoResponse(
            Integer idUsuario,
            Integer idVideo,
            Boolean liked,
            Integer vistas,
            Integer likesAutomaticos,
            Integer totalLikes
    ) {}

    @PostMapping("/like")
    public ResponseEntity<ProgresoVideoResponse> registrarLike(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idVideo,
            @Valid @RequestBody LikeRequest datos) {

        ProgresoVideo progreso = progresoVideoService.registrarLike(
                idUsuario,
                idVideo,
                datos.liked()
        );

        return ResponseEntity.ok(convertirRespuesta(progreso));
    }

    @PostMapping("/vistas")
    public ResponseEntity<ProgresoVideoResponse> aumentarVistas(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idVideo,
            @Valid @RequestBody VistasRequest datos) {

        ProgresoVideo progreso = progresoVideoService.aumentarVistas(
                idUsuario,
                idVideo,
                datos.cantidad()
        );

        return ResponseEntity.ok(convertirRespuesta(progreso));
    }

    @GetMapping
    public ResponseEntity<ProgresoVideoResponse> obtenerProgreso(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idVideo) {

        ProgresoVideo progreso = progresoVideoService.obtenerProgreso(
                idUsuario,
                idVideo
        );

        return ResponseEntity.ok(convertirRespuesta(progreso));
    }

    private ProgresoVideoResponse convertirRespuesta(ProgresoVideo progreso) {
        int likesAutomaticos = progreso.getLikesAutomaticos() != null
                ? progreso.getLikesAutomaticos()
                : 0;

        int likeManual = Boolean.TRUE.equals(progreso.getLiked()) ? 1 : 0;

        return new ProgresoVideoResponse(
                progreso.getUsuario().getId(),
                progreso.getVideo().getIdVideo(),
                progreso.getLiked(),
                progreso.getVistas(),
                likesAutomaticos,
                likesAutomaticos + likeManual
        );
    }
}
