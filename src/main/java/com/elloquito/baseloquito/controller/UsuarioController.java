package com.elloquito.baseloquito.controller;

import com.elloquito.baseloquito.model.HistorialEstadistica;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.service.UsuarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
            @PositiveOrZero @Max(100) Integer cohesion
    ) {}

    public record AccesoRequest(
            @NotBlank @Size(max = 50) String username
    ) {}

    public record EstadisticasRequest(
            @NotNull @PositiveOrZero Integer seguidores,
            @NotNull @PositiveOrZero Integer dinero,
            @NotNull @PositiveOrZero @Max(100) Integer cohesion
    ) {}

    public record UsuarioResponse(
            Integer idUsuario,
            String username,
            Integer seguidores,
            Integer dinero,
            Integer cohesion
    ) {}

    public record HistorialResponse(
            Long idHistorial,
            LocalDate fecha,
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

    @PostMapping("/acceso")
    public UsuarioResponse accederUsuario(
            @Valid @RequestBody AccesoRequest datos) {
        System.out.println(datos.toString());
        Usuario usuario = usuarioService.accederOCrearUsuario(datos.username());

        return convertirRespuesta(usuario);
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

    @PutMapping("/{id}/estadisticas")
    public UsuarioResponse actualizarEstadisticas(
            @PathVariable Integer id,
            @Valid @RequestBody EstadisticasRequest datos) {

        try {
            Usuario usuario = usuarioService.actualizarEstadisticas(
                    id,
                    datos.seguidores(),
                    datos.dinero(),
                    datos.cohesion()
            );

            return convertirRespuesta(usuario);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage()
            );
        }
    }

    @GetMapping("/{id}/historial")
    public List<HistorialResponse> obtenerHistorial(
            @PathVariable Integer id) {

        try {
            return usuarioService.obtenerHistorialEstadisticas(id)
                    .stream()
                    .map(this::convertirHistorial)
                    .toList();
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        }
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

    private HistorialResponse convertirHistorial(HistorialEstadistica historial) {
        return new HistorialResponse(
                historial.getId(),
                historial.getFecha(),
                historial.getSeguidores(),
                historial.getDinero(),
                historial.getCohesion()
        );
    }
}
