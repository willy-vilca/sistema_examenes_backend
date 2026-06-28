package com.sistemaexamenes.dto.pregunta;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReplicarPreguntasRequestDTO {

    private Long procesoDestinoId;

    private List<Long> preguntasIds;

}
