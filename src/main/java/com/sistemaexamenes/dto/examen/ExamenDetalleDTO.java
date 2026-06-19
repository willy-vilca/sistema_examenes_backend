package com.sistemaexamenes.dto.examen;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ExamenDetalleDTO {

    private Long id;

    private String nombre;

    private String procesoNombre;

    private String usuarioNombre;

    private Integer cantidadTemas;

    private LocalDateTime fechaGeneracion;

    private List<CategoriaDetalleDTO>
            categorias;

    private List<TemaDTO>
            temas;
}
