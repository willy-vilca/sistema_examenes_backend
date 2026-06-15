package com.sistemaexamenes.dto.pregunta;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlternativaRequestDTO {

    private String contenidoHtml;

    private Boolean esCorrecta;
}