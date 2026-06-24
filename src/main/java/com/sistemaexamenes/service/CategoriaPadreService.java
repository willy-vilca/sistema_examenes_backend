package com.sistemaexamenes.service;

import com.sistemaexamenes.dto.categoriaPadre.CategoriaPadreRequestDTO;
import com.sistemaexamenes.dto.categoriaPadre.CategoriaPadreResponseDTO;

import java.util.List;

public interface CategoriaPadreService {

    List<CategoriaPadreResponseDTO> listar();

    CategoriaPadreResponseDTO obtenerPorId(Long id);

    CategoriaPadreResponseDTO crear(CategoriaPadreRequestDTO dto);

    CategoriaPadreResponseDTO actualizar(Long id, CategoriaPadreRequestDTO dto);

    void eliminar(Long id);

}