package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository
        extends JpaRepository<Pregunta, Long> {

    List<Pregunta> findByProcesoId(Long procesoId);

    List<Pregunta> findByCategoriaId(Long categoriaId);

    List<Pregunta> findByActivoTrue();

    List<Pregunta> findByProcesoIdAndActivoTrue(
            Long procesoId);

    List<Pregunta> findByProcesoIdAndCategoriaId(
            Long procesoId,
            Long categoriaId);

    List<Pregunta>
    findByProcesoIdAndCategoriaIdAndActivoTrue(
            Long procesoId,
            Long categoriaId
    );

    Long countByProcesoIdAndCategoriaIdAndActivoTrue(
            Long procesoId,
            Long categoriaId
    );
}