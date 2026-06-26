package com.sistemaexamenes.service;

import com.sistemaexamenes.dto.examen.ExamenGeneradoResponseDTO;
import com.sistemaexamenes.dto.examen.GenerarExamenRequestDTO;

public interface ExamenService {

    ExamenGeneradoResponseDTO
    generarExamen(
            GenerarExamenRequestDTO request
    );
    void eliminarExamen(Long examenId);
}
