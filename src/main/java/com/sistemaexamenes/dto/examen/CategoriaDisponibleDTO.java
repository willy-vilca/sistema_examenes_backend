package com.sistemaexamenes.dto.examen;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoriaDisponibleDTO {

    private Long categoriaId;

    private String categoriaNombre;

    private Long preguntasDisponibles;
}
