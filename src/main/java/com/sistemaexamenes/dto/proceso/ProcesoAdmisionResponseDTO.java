package com.sistemaexamenes.dto.proceso;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProcesoAdmisionResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private LocalDate fecha_inicio;

    private LocalDate fecha_fin;

    private String estado;

    private LocalDateTime fecha_creacion;
}
