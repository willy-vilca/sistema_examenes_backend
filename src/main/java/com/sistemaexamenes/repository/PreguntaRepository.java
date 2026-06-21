package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("""
        SELECT COUNT(p)
        FROM Pregunta p
        WHERE p.proceso.id = :procesoId
        AND p.categoria.id = :categoriaId
        AND p.activo = true
    """)
    Long contarPreguntasActivasPorProcesoYCategoria(
            Long procesoId,
            Long categoriaId
    );

    @Query("""
        SELECT COUNT(p)
        FROM Pregunta p
        WHERE p.activo = true
    """)
    Long contarPreguntasActivas();

    @Query("""
        SELECT p.categoria.id,
               p.categoria.nombre,
               COUNT(p)
        FROM Pregunta p
        WHERE p.activo = true
        GROUP BY p.categoria.id,
                 p.categoria.nombre
        ORDER BY COUNT(p) DESC
    """)
    List<Object[]> obtenerPreguntasPorCategoria();

    @Query("""
        SELECT c.id,
               c.nombre,
               COUNT(p)
        FROM Categoria c
        LEFT JOIN Pregunta p
               ON p.categoria.id = c.id
               AND p.activo = true
        GROUP BY c.id,
                 c.nombre
        ORDER BY COUNT(p) ASC
    """)
    List<Object[]> obtenerCategoriasConMenosPreguntas();
}