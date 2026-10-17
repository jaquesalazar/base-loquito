package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.Short;
import com.elloquito.baseloquito.service.ShortService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shorts")
public class ShortController {

    private final ShortService shortService;

    public ShortController(ShortService shortService) {
        this.shortService = shortService;
    }

    public record ShortRequest(
            @NotBlank String titulo,
            @NotNull @PositiveOrZero Integer vistas,
            @NotNull @PositiveOrZero Integer likes,
            @NotNull Integer idUsuario
    ) {}

    public record ShortResponse(
            Integer idVideo,
            String titulo,
            Integer vistas,
            Integer likes,
            Integer idUsuario
    ) {}

    @GetMapping
    public List<ShortResponse> obtenerShorts() {
        return shortService.obtenerShorts()
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ShortResponse> crearShort(
            @Valid @RequestBody ShortRequest datos) {

        Short guardado = shortService.crearShort(
                datos.titulo(),
                datos.vistas(),
                datos.likes(),
                datos.idUsuario()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertirRespuesta(guardado));
    }

    private ShortResponse convertirRespuesta(Short shortVideo) {
        return new ShortResponse(
                shortVideo.getIdVideo(),
                shortVideo.getTitulo(),
                shortVideo.getVistas(),
                shortVideo.getLikes(),
                shortVideo.getUsuario().getId()
        );
    }
}
