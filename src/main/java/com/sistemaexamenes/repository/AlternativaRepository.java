package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.Alternativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlternativaRepository
        extends JpaRepository<Alternativa, Long> {
}