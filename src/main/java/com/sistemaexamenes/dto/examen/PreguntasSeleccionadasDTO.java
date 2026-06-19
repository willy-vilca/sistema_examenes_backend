package com.sistemaexamenes.dto.examen;

import com.sistemaexamenes.entity.Pregunta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PreguntasSeleccionadasDTO {

    private Long categoriaId;

    private String categoriaNombre;

    private List<Pregunta> preguntas;
}
