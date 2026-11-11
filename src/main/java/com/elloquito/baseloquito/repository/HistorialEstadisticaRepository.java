package com.elloquito.baseloquito.repository;

import com.elloquito.baseloquito.model.HistorialEstadistica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistorialEstadisticaRepository extends JpaRepository<HistorialEstadistica, Long> {

    List<HistorialEstadistica> findByUsuarioIdOrderByFechaAsc(Integer idUsuario);

    Optional<HistorialEstadistica> findByUsuarioIdAndFecha(Integer idUsuario, LocalDate fecha);
}
