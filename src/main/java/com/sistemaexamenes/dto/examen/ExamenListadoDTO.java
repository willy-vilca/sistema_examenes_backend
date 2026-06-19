package com.sistemaexamenes.dto.examen;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ExamenListadoDTO {

    private Long id;

    private String nombre;

    private String procesoNombre;

    private String usuarioNombre;

    private Integer cantidadTemas;

    private LocalDateTime fechaGeneracion;
}
