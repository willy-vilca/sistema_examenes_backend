package com.sistemaexamenes.mapper;

import com.sistemaexamenes.dto.proceso.ProcesoAdmisionRequestDTO;
import com.sistemaexamenes.dto.proceso.ProcesoAdmisionResponseDTO;
import com.sistemaexamenes.entity.ProcesoAdmision;

public class ProcesoAdmisionMapper {

    public static ProcesoAdmision toEntity(
            ProcesoAdmisionRequestDTO dto) {

        ProcesoAdmision proceso = new ProcesoAdmision();

        proceso.setNombre(dto.getNombre());
        proceso.setDescripcion(dto.getDescripcion());
        proceso.setFecha_inicio(dto.getFecha_inicio());
        proceso.setFecha_fin(dto.getFecha_fin());
        proceso.setEstado(dto.getEstado());

        return proceso;
    }

    public static ProcesoAdmisionResponseDTO toResponse(
            ProcesoAdmision proceso) {

        return ProcesoAdmisionResponseDTO.builder()
                .id(proceso.getId())
                .nombre(proceso.getNombre())
                .descripcion(proceso.getDescripcion())
                .fecha_inicio(proceso.getFecha_inicio())
                .fecha_fin(proceso.getFecha_fin())
                .estado(proceso.getEstado())
                .fecha_creacion(proceso.getFecha_creacion())
                .build();
    }
}