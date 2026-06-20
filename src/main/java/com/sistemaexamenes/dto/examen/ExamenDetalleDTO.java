package com.sistemaexamenes.dto.examen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamenDetalleDTO {

    private Long id;

    private String nombre;

    private String procesoNombre;

    private String usuarioNombre;

    private LocalDateTime fechaGeneracion;

    private Integer cantidadTemas;

    private List<CategoriaResumenDTO> categorias;

    private List<TemaDTO> temas;

}
