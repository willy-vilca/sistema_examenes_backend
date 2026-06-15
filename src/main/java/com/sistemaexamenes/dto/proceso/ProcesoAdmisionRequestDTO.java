package com.sistemaexamenes.dto.proceso;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProcesoAdmisionRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    private LocalDate fecha_inicio;

    private LocalDate fecha_fin;

    private String estado;
}
