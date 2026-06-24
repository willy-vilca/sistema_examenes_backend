package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.CategoriaPadre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaPadreRepository extends JpaRepository<CategoriaPadre, Long> {

    Optional<CategoriaPadre> findByNombreIgnoreCase(String nombre);

}
