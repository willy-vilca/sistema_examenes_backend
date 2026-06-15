package com.sistemaexamenes.service;

import com.sistemaexamenes.dto.proceso.ProcesoAdmisionRequestDTO;
import com.sistemaexamenes.dto.proceso.ProcesoAdmisionResponseDTO;

import java.util.List;

public interface ProcesoAdmisionService {

    ProcesoAdmisionResponseDTO crear(
            ProcesoAdmisionRequestDTO dto);

    List<ProcesoAdmisionResponseDTO> listar();

    ProcesoAdmisionResponseDTO buscarPorId(Long id);

    ProcesoAdmisionResponseDTO actualizar(
            Long id,
            ProcesoAdmisionRequestDTO dto);

    void eliminar(Long id);
}
