package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.ExamenCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenCategoriaRepository
        extends JpaRepository<ExamenCategoria, Long> {

    List<ExamenCategoria>
    findByExamenGeneradoId(Long examenId);
}
