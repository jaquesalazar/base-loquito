package com.elloquito.baseloquito.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "jugador_videos")
public class JugadorVideo {

    @EmbeddedId
    private JugadorVideoId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @MapsId("idVideo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_video", nullable = false)
    private Video video;

    public JugadorVideo() {
    }

    public JugadorVideoId getId() {
        return id;
    }

    public void setId(JugadorVideoId id) {
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
}
