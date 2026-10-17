package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.service.UsuarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Max;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public record UsuarioRequest(
            @NotNull @PositiveOrZero Integer seguidores,
            @NotNull @PositiveOrZero Integer dinero,
            @NotNull @PositiveOrZero @Max(100) Integer influencia,
            @NotNull @PositiveOrZero @Max(100) Integer cohesion
    ) {}

    public record UsuarioResponse(
            Integer idUsuario,
            Integer seguidores,
            Integer dinero,
            Integer influencia,
            Integer cohesion
    ) {}

    @GetMapping
    public List<UsuarioResponse> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios()
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(
            @Valid @RequestBody UsuarioRequest datos) {

        Usuario guardado = usuarioService.crearUsuario(
                datos.seguidores(),
                datos.dinero(),
                datos.influencia(),
                datos.cohesion()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertirRespuesta(guardado));
    }

    private UsuarioResponse convertirRespuesta(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getSeguidores(),
                usuario.getDinero(),
                usuario.getInfluencia(),
                usuario.getCohesion()
        );
    }
}
