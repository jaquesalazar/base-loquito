
package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.Comentario;
import com.elloquito.baseloquito.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/videos/{idVideo}/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public List<ComentarioResponse> obtenerComentarios(
            @PathVariable Integer idVideo,
            @RequestParam Integer idUsuario) {

        return comentarioService.obtenerComentariosPorVideo(idUsuario, idVideo)
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioResponse crearComentario(
            @PathVariable Integer idVideo,
            @RequestBody ComentarioRequest request) {

        Comentario comentario = comentarioService.crearComentario(
                request.idUsuario(),
                idVideo,
                request.texto()
        );

        return convertirRespuesta(comentario);
    }

    @DeleteMapping("/{idComentario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarComentario(
            @PathVariable Integer idVideo,
            @PathVariable Integer idComentario,
            @RequestParam Integer idUsuario) {

        comentarioService.eliminarComentario(idVideo, idComentario, idUsuario);
    }

    private ComentarioResponse convertirRespuesta(Comentario comentario) {
        return new ComentarioResponse(
                comentario.getIdComentario(),
                comentario.getUsuario().getId(),
                comentario.getUsuario().getUsername(),
                comentario.getVideo().getIdVideo(),
                comentario.getTexto(),
                comentario.getCreatedAt()
        );
    }

    public record ComentarioRequest(
            Integer idUsuario,
            String texto
    ) {
    }

    public record ComentarioResponse(
            Integer idComentario,
            Integer idUsuario,
            String username,
            Integer idVideo,
            String texto,
            LocalDateTime createdAt
    ) {
    }
}
