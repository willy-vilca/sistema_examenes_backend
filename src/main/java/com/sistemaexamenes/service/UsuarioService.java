package com.sistemaexamenes.service;

import com.sistemaexamenes.dto.usuario.UsuarioRequestDTO;
import com.sistemaexamenes.dto.usuario.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> listar();

    UsuarioResponseDTO obtenerPorId(Long id);

    UsuarioResponseDTO crear(UsuarioRequestDTO dto);

    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto);

    void cambiarEstado(Long id);

}
