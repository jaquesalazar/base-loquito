package com.elloquito.baseloquito.repository;

import com.elloquito.baseloquito.model.JugadorVideo;
import com.elloquito.baseloquito.model.JugadorVideoId;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorVideoRepository extends JpaRepository<JugadorVideo, JugadorVideoId> {

    @EntityGraph(attributePaths = "video")
    List<JugadorVideo> findByUsuario_IdOrderByVideo_IdVideoAsc(Integer idUsuario);

    @EntityGraph(attributePaths = {"usuario", "video"})
    List<JugadorVideo> findAllByOrderByUsuario_IdAscVideo_IdVideoAsc();

    boolean existsByUsuario_IdAndVideo_IdVideo(Integer idUsuario, Integer idVideo);
}
