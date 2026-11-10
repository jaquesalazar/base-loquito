package com.elloquito.baseloquito.repository;

import com.elloquito.baseloquito.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    @EntityGraph(attributePaths = {"usuario", "video"})
    List<Comentario> findByUsuario_IdAndVideo_IdVideoOrderByCreatedAtDesc(
            Integer idUsuario,
            Integer idVideo
    );

    @EntityGraph(attributePaths = {"usuario", "video"})
    List<Comentario> findByUsuario_IdOrderByCreatedAtDesc(Integer idUsuario);
}
