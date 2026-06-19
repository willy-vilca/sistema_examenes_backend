package com.sistemaexamenes.dto.examen;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenerarExamenRequestDTO {

    private String nombreExamen;

    private Long procesoId;

    private Integer cantidadTemas;

    private List<CategoriaSeleccionadaDTO>
            categorias;
}