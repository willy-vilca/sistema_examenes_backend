package com.sistemaexamenes.dto.examen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResumenDTO {

    private Long categoriaId;

    private String categoriaNombre;

    private Integer cantidadPreguntas;

}
