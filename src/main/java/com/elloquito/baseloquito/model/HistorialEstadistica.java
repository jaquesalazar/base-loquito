package com.elloquito.baseloquito.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "historial_estadisticas",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_historial_estadisticas_usuario_fecha",
            columnNames = {"id_usuario", "fecha"}
        )
    }
)
public class HistorialEstadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "seguidores", nullable = false)
    private Integer seguidores;

    @Column(name = "dinero", nullable = false)
    private Integer dinero;

    @Column(name = "cohesion", nullable = false)
    private Integer cohesion;

    public HistorialEstadistica() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(Integer seguidores) {
        this.seguidores = seguidores;
    }

    public Integer getDinero() {
        return dinero;
    }

    public void setDinero(Integer dinero) {
        this.dinero = dinero;
    }

    public Integer getCohesion() {
        return cohesion;
    }

    public void setCohesion(Integer cohesion) {
        this.cohesion = cohesion;
    }
}
