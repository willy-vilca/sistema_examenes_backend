package com.sistemaexamenes.dto.examen;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExamenGeneradoResponseDTO {

    private Long id;

    private String nombre;
}
