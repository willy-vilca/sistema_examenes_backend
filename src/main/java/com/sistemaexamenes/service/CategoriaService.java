package com.sistemaexamenes.service;

import com.sistemaexamenes.dto.categoria.CategoriaRequestDTO;
import com.sistemaexamenes.dto.categoria.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {

    CategoriaResponseDTO crear(CategoriaRequestDTO dto);

    List<CategoriaResponseDTO> listar();

    CategoriaResponseDTO buscarPorId(Long id);

    CategoriaResponseDTO actualizar(Long id,
                                    CategoriaRequestDTO dto);

    void eliminar(Long id);
}
