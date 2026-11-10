// Controlador REST para administrar los videos asignados a cada jugador.

package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.JugadorVideo;
import com.elloquito.baseloquito.service.JugadorVideoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/{idUsuario}/videos")
public class JugadorVideoController {

    private final JugadorVideoService jugadorVideoService;

    public JugadorVideoController(JugadorVideoService jugadorVideoService) {
        this.jugadorVideoService = jugadorVideoService;
    }

    public record VideoAsignadoResponse(
            Integer idUsuario,
            Integer idVideo,
            String titulo,
            String videoUrl,
            String caption,
            String tag,
            String hashtags
    ) {}

    @PostMapping("/{idVideo}")
    public ResponseEntity<VideoAsignadoResponse> asignarVideo(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idVideo) {

        JugadorVideo asignacion = jugadorVideoService.asignarVideo(
                idUsuario,
                idVideo
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(convertirRespuesta(asignacion));
    }

    @GetMapping
    public ResponseEntity<List<VideoAsignadoResponse>> obtenerVideosPorJugador(
            @PathVariable Integer idUsuario) {

        List<VideoAsignadoResponse> videos = jugadorVideoService
                .obtenerVideosPorJugador(idUsuario)
                .stream()
                .map(this::convertirRespuesta)
                .toList();

        return ResponseEntity.ok(videos);
    }

    private VideoAsignadoResponse convertirRespuesta(JugadorVideo asignacion) {
        return new VideoAsignadoResponse(
                asignacion.getUsuario().getId(),
                asignacion.getVideo().getIdVideo(),
                asignacion.getVideo().getTitulo(),
                asignacion.getVideo().getVideoUrl(),
                asignacion.getVideo().getCaption(),
                asignacion.getVideo().getTag(),
                asignacion.getVideo().getHashtags()
        );
    }
}
