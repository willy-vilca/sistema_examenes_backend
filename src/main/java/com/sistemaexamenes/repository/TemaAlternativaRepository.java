package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.TemaAlternativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemaAlternativaRepository
        extends JpaRepository<TemaAlternativa, Long> {

    List<TemaAlternativa>
    findByTemaPreguntaIdOrderByOrdenAlternativaAsc(
            Long temaPreguntaId
    );
}