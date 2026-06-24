package com.sistemaexamenes.dto.categoriaPadre;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaPadreResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;

}