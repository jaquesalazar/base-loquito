
package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.Short;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.repository.ShortRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shorts")
public class ShortController {

    private final ShortRepository shortRepository;
    private final EntityManager entityManager;

    public ShortController(
            ShortRepository shortRepository,
            EntityManager entityManager) {
        this.shortRepository = shortRepository;
        this.entityManager = entityManager;
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
    @Transactional(readOnly = true)
    public List<ShortResponse> obtenerShorts() {
        return shortRepository.findAll()
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ShortResponse> crearShort(
            @Valid @RequestBody ShortRequest datos) {

        Usuario usuario = entityManager.find(
                Usuario.class, datos.idUsuario());

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        Short shortNuevo = new Short();
        shortNuevo.setTitulo(datos.titulo());
        shortNuevo.setVistas(datos.vistas());
        shortNuevo.setLikes(datos.likes());
        shortNuevo.setUsuario(usuario);

        Short guardado = shortRepository.save(shortNuevo);

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
