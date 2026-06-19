package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.TemaExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemaExamenRepository
        extends JpaRepository<TemaExamen, Long> {

    List<TemaExamen>
    findByExamenGeneradoIdOrderByCodigoTemaAsc(
            Long examenId
    );
}
