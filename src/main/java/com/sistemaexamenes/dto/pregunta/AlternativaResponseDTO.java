package com.sistemaexamenes.dto.pregunta;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AlternativaResponseDTO {

    private Long id;

    private String contenidoHtml;

    private Boolean esCorrecta;
}