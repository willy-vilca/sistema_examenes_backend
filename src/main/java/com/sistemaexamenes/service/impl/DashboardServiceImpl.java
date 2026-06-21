package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.dashboard.CategoriaCriticaDTO;
import com.sistemaexamenes.dto.dashboard.DashboardResumenDTO;
import com.sistemaexamenes.dto.dashboard.PreguntasPorCategoriaDTO;
import com.sistemaexamenes.dto.dashboard.UltimoExamenDTO;
import com.sistemaexamenes.entity.ExamenGenerado;
import com.sistemaexamenes.repository.CategoriaRepository;
import com.sistemaexamenes.repository.ExamenGeneradoRepository;
import com.sistemaexamenes.repository.PreguntaRepository;
import com.sistemaexamenes.repository.ProcesoAdmisionRepository;
import com.sistemaexamenes.service.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final CategoriaRepository categoriaRepository;
    private final ProcesoAdmisionRepository procesoRepository;
    private final PreguntaRepository preguntaRepository;
    private final ExamenGeneradoRepository examenRepository;

    @Override
    public DashboardResumenDTO obtenerResumen() {

        Long totalCategorias = categoriaRepository.count();
        Long totalProcesos = procesoRepository.count();
        Long totalPreguntas = preguntaRepository.contarPreguntasActivas();
        Long totalExamenes = examenRepository.count();

        List<PreguntasPorCategoriaDTO> preguntasPorCategoria = new ArrayList<>();

        List<Object[]> preguntasCategoria = preguntaRepository.obtenerPreguntasPorCategoria();

        for (Object[] fila : preguntasCategoria) {

            preguntasPorCategoria.add(
                    new PreguntasPorCategoriaDTO(
                            (Long) fila[0],
                            (String) fila[1],
                            (Long) fila[2]
                    )
            );

        }

        List<CategoriaCriticaDTO> categoriasCriticas = new ArrayList<>();

        List<Object[]> categoriasMenores = preguntaRepository.obtenerCategoriasConMenosPreguntas();

        int limiteCriticas =
                Math.min(
                        5,
                        categoriasMenores.size()
                );

        for (int i = 0; i < limiteCriticas; i++) {

            Object[] fila = categoriasMenores.get(i);

            categoriasCriticas.add(
                    new CategoriaCriticaDTO(
                            (Long) fila[0],
                            (String) fila[1],
                            (Long) fila[2]
                    )
            );

        }

        List<UltimoExamenDTO> ultimosExamenes = new ArrayList<>();

        List<ExamenGenerado> examenes = examenRepository.obtenerUltimosExamenes();

        int limiteExamenes =
                Math.min(
                        5,
                        examenes.size()
                );

        for (int i = 0; i < limiteExamenes; i++) {

            ExamenGenerado examen = examenes.get(i);

            ultimosExamenes.add(
                    new UltimoExamenDTO(
                            examen.getId(),
                            examen.getNombre(),
                            examen.getProceso().getNombre(),
                            examen.getFechaGeneracion()
                    )
            );

        }

        return new DashboardResumenDTO(
                totalCategorias,
                totalProcesos,
                totalPreguntas,
                totalExamenes,
                preguntasPorCategoria,
                ultimosExamenes,
                categoriasCriticas
        );

    }

}
