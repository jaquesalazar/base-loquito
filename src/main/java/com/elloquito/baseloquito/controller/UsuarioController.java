package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.service.UsuarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

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
            @NotBlank @Size(max = 50) String username,
            @PositiveOrZero Integer seguidores,
            @PositiveOrZero Integer dinero,
            @PositiveOrZero Integer cohesion
    ) {}

    public record UsuarioResponse(
            Integer idUsuario,
            String username,
            Integer seguidores,
            Integer dinero,
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
                datos.username(),
                datos.seguidores(),
                datos.dinero(),
                datos.cohesion()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertirRespuesta(guardado));
    }

    private UsuarioResponse convertirRespuesta(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getSeguidores(),
                usuario.getDinero(),
                usuario.getCohesion()
        );
    }
}
