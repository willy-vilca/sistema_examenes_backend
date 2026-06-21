package com.sistemaexamenes.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResumenDTO {

    private Long totalCategorias;
    private Long totalProcesos;
    private Long totalPreguntas;
    private Long totalExamenes;

    private List<PreguntasPorCategoriaDTO> preguntasPorCategoria;

    private List<UltimoExamenDTO> ultimosExamenes;

    private List<CategoriaCriticaDTO> categoriasCriticas;

}
