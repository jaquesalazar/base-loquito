
package com.elloquito.baseloquito.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "short")
public class Short {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_video")
    private Integer idVideo;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false)
    private Integer vistas = 0;

    @Column(nullable = false)
    private Integer likes = 0;

    @ManyToOne
    @JoinColumn(
        name = "id_usuario",
        referencedColumnName = "id_usuario",
        nullable = false
    )
    private Usuario usuario;

    public Short() {
    }

    public Integer getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Integer idVideo) {
        this.idVideo = idVideo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getVistas() {
        return vistas;
    }

    public void setVistas(Integer vistas) {
        this.vistas = vistas;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
