package com.sistemaexamenes.dto.pregunta;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PreguntaResponseDTO {

    private Long id;

    private Long procesoId;

    private String procesoNombre;

    private Long categoriaId;

    private String categoriaNombre;

    private String contenidoHtml;

    private Boolean activo;

    private LocalDateTime fechaCreacion;

    private List<AlternativaResponseDTO> alternativas;
}