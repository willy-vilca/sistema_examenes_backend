package com.sistemaexamenes.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreguntasPorCategoriaDTO {

    private Long categoriaId;
    private String categoriaNombre;
    private Long cantidadPreguntas;

}
