
package com.elloquito.baseloquito.repository;

import com.elloquito.baseloquito.model.ProgresoVideo;
import com.elloquito.baseloquito.model.ProgresoVideoId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProgresoVideoRepository extends JpaRepository<ProgresoVideo, ProgresoVideoId> {

    Optional<ProgresoVideo> findByIdIdUsuarioAndIdIdVideo(Integer idUsuario, Integer idVideo);

    List<ProgresoVideo> findByIdIdUsuario(Integer idUsuario);

    List<ProgresoVideo> findByIdIdUsuarioAndLikedTrue(Integer idUsuario);

    List<ProgresoVideo> findAllByOrderByIdIdUsuarioAscIdIdVideoAsc();
}

