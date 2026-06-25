package com.sistemaexamenes.repository;

import com.sistemaexamenes.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findAllByOrderByNombreAsc();
    boolean existsByCorreo(String correo);
}