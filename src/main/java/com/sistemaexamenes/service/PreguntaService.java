package com.sistemaexamenes.service;

import com.sistemaexamenes.dto.pregunta.PreguntaRequestDTO;
import com.sistemaexamenes.dto.pregunta.PreguntaResponseDTO;

import java.util.List;

public interface PreguntaService {

    PreguntaResponseDTO crear(
            PreguntaRequestDTO dto);

    PreguntaResponseDTO actualizar(
            Long id,
            PreguntaRequestDTO dto);

    List<PreguntaResponseDTO> listar();

    PreguntaResponseDTO buscarPorId(Long id);

    void eliminar(Long id);

    void cambiarEstado(Long id);
}
