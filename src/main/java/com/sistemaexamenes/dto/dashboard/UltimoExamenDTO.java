package com.sistemaexamenes.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UltimoExamenDTO {

    private Long id;

    private String nombre;

    private String procesoNombre;

    private LocalDateTime fechaGeneracion;

}
