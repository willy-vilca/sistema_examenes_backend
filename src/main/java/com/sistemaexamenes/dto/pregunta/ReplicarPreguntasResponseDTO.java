package com.sistemaexamenes.dto.pregunta;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReplicarPreguntasResponseDTO {

    private Integer preguntasCopiadas;

    private Integer preguntasOmitidas;

}
