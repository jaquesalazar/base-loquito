package com.elloquito.baseloquito.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "progreso_videos")
public class ProgresoVideo {

    @EmbeddedId
    private ProgresoVideoId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @MapsId("idVideo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_video", nullable = false)
    private Video video;

    @Column(nullable = false)
    private Boolean liked = false;

    @Column(nullable = false)
    private Integer vistas = 0;

    @Column(name = "likes_automaticos", nullable = false)
    private Integer likesAutomaticos = 0;

    public ProgresoVideo() {
    }

    public ProgresoVideoId getId() {
        return id;
    }

    public void setId(ProgresoVideoId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Integer getVistas() {
        return vistas;
    }

    public void setVistas(Integer vistas) {
        this.vistas = vistas;
    }

    public Integer getLikesAutomaticos() {
        return likesAutomaticos;
    }

    public void setLikesAutomaticos(Integer likesAutomaticos) {
        this.likesAutomaticos = likesAutomaticos;
    }
}
