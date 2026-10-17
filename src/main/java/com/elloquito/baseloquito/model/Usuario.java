
package com.elloquito.baseloquito.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(nullable = false)
    private Integer seguidores = 0;

    @Column(nullable = false)
    private Integer dinero = 0;

    @Column(nullable = false)
    private Integer influencia = 0;

    @Column(nullable = false)
    private Integer cohesion = 0;

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getInfluencia() {
        return influencia;
    }

    public void setInfluencia(Integer influencia) {
        this.influencia = influencia;
    }

    public Integer getCohesion() {
        return cohesion;
    }

    public void setCohesion(Integer cohesion) {
        this.cohesion = cohesion;
    }
}
