package com.sistemaexamenes.dto.examen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamenListadoDTO {

    private Long id;

    private String nombre;

    private String procesoNombre;

    private String usuarioNombre;

    private Integer cantidadTemas;

    private LocalDateTime fechaGeneracion;

}