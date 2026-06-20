package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository
        extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNombreIgnoreCase(
            String nombre);

    @Query("""
        SELECT DISTINCT p.categoria
        FROM Pregunta p
        WHERE p.proceso.id = :procesoId
        AND p.activo = true
        ORDER BY p.categoria.nombre
    """)
    List<Categoria> obtenerCategoriasConPreguntasPorProceso(
            Long procesoId
    );
}
