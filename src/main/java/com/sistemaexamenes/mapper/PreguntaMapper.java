package com.sistemaexamenes.mapper;

import com.sistemaexamenes.dto.pregunta.AlternativaResponseDTO;
import com.sistemaexamenes.dto.pregunta.PreguntaResponseDTO;
import com.sistemaexamenes.entity.Alternativa;
import com.sistemaexamenes.entity.Pregunta;

import java.util.List;

public class PreguntaMapper {

    public static PreguntaResponseDTO toResponse(
            Pregunta pregunta) {

        List<AlternativaResponseDTO> alternativas =
                pregunta.getAlternativas()
                        .stream()
                        .map(PreguntaMapper::toAlternativaResponse)
                        .toList();

        return PreguntaResponseDTO.builder()
                .id(pregunta.getId())
                .procesoId(pregunta.getProceso().getId())
                .procesoNombre(pregunta.getProceso().getNombre())
                .categoriaId(pregunta.getCategoria().getId())
                .categoriaNombre(pregunta.getCategoria().getNombre())
                .categoriaPadreId(
                        pregunta.getCategoria()
                                .getCategoriaPadre() != null
                                ? pregunta.getCategoria().getCategoriaPadre().getId()
                                : null
                )
                .categoriaPadreNombre(
                        pregunta.getCategoria()
                                .getCategoriaPadre() != null
                                ? pregunta.getCategoria().getCategoriaPadre().getNombre()
                                : null
                )
                .contenidoHtml(pregunta.getContenidoHtml())
                .activo(pregunta.getActivo())
                .fechaCreacion(pregunta.getFechaCreacion())
                .alternativas(alternativas)
                .build();
    }

    private static AlternativaResponseDTO
    toAlternativaResponse(
            Alternativa alternativa) {

        return AlternativaResponseDTO.builder()
                .id(alternativa.getId())
                .contenidoHtml(
                        alternativa.getContenidoHtml())
                .esCorrecta(
                        alternativa.getEsCorrecta())
                .build();
    }
}
