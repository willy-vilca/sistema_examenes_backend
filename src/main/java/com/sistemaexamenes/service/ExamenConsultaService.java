package com.sistemaexamenes.service;

import com.sistemaexamenes.dto.examen.CategoriaDisponibleDTO;
import com.sistemaexamenes.dto.examen.ExamenDetalleDTO;
import com.sistemaexamenes.dto.examen.ExamenListadoDTO;

import java.util.List;

public interface ExamenConsultaService {

    List<CategoriaDisponibleDTO> obtenerCategoriasDisponibles(
            Long procesoId
    );

    List<ExamenListadoDTO> listarExamenes();

    ExamenDetalleDTO obtenerDetalleExamen(
            Long examenId
    );

}
