package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.ProcesoAdmision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcesoAdmisionRepository
        extends JpaRepository<ProcesoAdmision, Long> {
}
