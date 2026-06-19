package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.TemaPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemaPreguntaRepository
        extends JpaRepository<TemaPregunta, Long> {

    List<TemaPregunta>
    findByTemaIdOrderByOrdenPreguntaAsc(
            Long temaId
    );
}
