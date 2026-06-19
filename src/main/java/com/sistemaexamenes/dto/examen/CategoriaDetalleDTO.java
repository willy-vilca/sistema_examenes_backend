package com.sistemaexamenes.dto.examen;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoriaDetalleDTO {

    private String categoriaNombre;

    private Integer cantidadPreguntas;
}
