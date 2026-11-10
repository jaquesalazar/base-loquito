package com.elloquito.baseloquito.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProgresoVideoId implements Serializable {

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_video")
    private Integer idVideo;

    public ProgresoVideoId() {
    }

    public ProgresoVideoId(Integer idUsuario, Integer idVideo) {
        this.idUsuario = idUsuario;
        this.idVideo = idVideo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Integer idVideo) {
        this.idVideo = idVideo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgresoVideoId)) return false;
        ProgresoVideoId that = (ProgresoVideoId) o;
        return Objects.equals(idUsuario, that.idUsuario)
                && Objects.equals(idVideo, that.idVideo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idVideo);
    }
}
