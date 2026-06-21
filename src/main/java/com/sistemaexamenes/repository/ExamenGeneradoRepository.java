package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.ExamenGenerado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenGeneradoRepository
        extends JpaRepository<ExamenGenerado, Long> {

    List<ExamenGenerado>
    findAllByOrderByFechaGeneracionDesc();

    List<ExamenGenerado>
    findByProcesoId(Long procesoId);

    @Query("""
        SELECT e
        FROM ExamenGenerado e
        ORDER BY e.fechaGeneracion DESC
    """)
    List<ExamenGenerado> obtenerUltimosExamenes();
}
