package com.sistemaexamenes.dto.categoria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoriaResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private Long categoriaPadreId;
    private String categoriaPadreNombre;
}
