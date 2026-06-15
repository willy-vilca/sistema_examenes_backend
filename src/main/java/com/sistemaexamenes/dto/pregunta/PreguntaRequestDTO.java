package com.sistemaexamenes.dto.pregunta;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreguntaRequestDTO {

    private Long procesoId;

    private Long categoriaId;

    private String contenidoHtml;

    private List<AlternativaRequestDTO> alternativas;
}
