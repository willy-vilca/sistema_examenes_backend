package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.examen.CategoriaDisponibleDTO;
import com.sistemaexamenes.dto.examen.CategoriaResumenDTO;
import com.sistemaexamenes.dto.examen.ExamenDetalleDTO;
import com.sistemaexamenes.dto.examen.ExamenListadoDTO;
import com.sistemaexamenes.dto.examen.TemaDTO;
import com.sistemaexamenes.entity.Categoria;
import com.sistemaexamenes.entity.ExamenGenerado;
import com.sistemaexamenes.entity.TemaExamen;
import com.sistemaexamenes.entity.TemaPregunta;
import com.sistemaexamenes.repository.CategoriaRepository;
import com.sistemaexamenes.repository.ExamenGeneradoRepository;
import com.sistemaexamenes.repository.PreguntaRepository;
import com.sistemaexamenes.repository.TemaExamenRepository;
import com.sistemaexamenes.repository.TemaPreguntaRepository;
import com.sistemaexamenes.service.ExamenConsultaService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExamenConsultaServiceImpl
        implements ExamenConsultaService {

    private final CategoriaRepository categoriaRepository;
    private final PreguntaRepository preguntaRepository;
    private final ExamenGeneradoRepository examenGeneradoRepository;
    private final TemaExamenRepository temaExamenRepository;
    private final TemaPreguntaRepository temaPreguntaRepository;

    @Override
    public List<CategoriaDisponibleDTO> obtenerCategoriasDisponibles(
            Long procesoId
    ) {

        List<Categoria> categorias =
                categoriaRepository
                        .obtenerCategoriasConPreguntasPorProceso(
                                procesoId
                        );

        List<CategoriaDisponibleDTO> resultado = new ArrayList<>();

        for (Categoria categoria : categorias) {

            Long cantidad =
                    preguntaRepository
                            .contarPreguntasActivasPorProcesoYCategoria(
                                    procesoId,
                                    categoria.getId()
                            );

            resultado.add(
                    new CategoriaDisponibleDTO(
                            categoria.getId(),
                            categoria.getNombre(),
                            cantidad
                    )
            );

        }

        return resultado;
    }

    @Override
    public List<ExamenListadoDTO> listarExamenes() {

        List<ExamenGenerado> examenes = examenGeneradoRepository.findAll();

        List<ExamenListadoDTO> resultado = new ArrayList<>();

        for (ExamenGenerado examen : examenes) {

            resultado.add(
                    new ExamenListadoDTO(
                            examen.getId(),
                            examen.getNombre(),
                            examen.getProceso().getNombre(),
                            examen.getUsuario().getNombre(),
                            examen.getCantidadTemas(),
                            examen.getFechaGeneracion()
                    )
            );

        }

        return resultado;
    }

    @Override
    public ExamenDetalleDTO obtenerDetalleExamen(
            Long examenId
    ) {

        ExamenGenerado examen =
                examenGeneradoRepository
                        .findById(examenId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Examen no encontrado"
                                )
                        );

        List<TemaExamen> temas =
                temaExamenRepository
                        .findByExamenGeneradoId(
                                examenId
                        );

        List<TemaDTO> temasDTO = new ArrayList<>();

        for (TemaExamen tema : temas) {

            temasDTO.add(
                    new TemaDTO(
                            tema.getId(),
                            tema.getCodigoTema(),
                            tema.getRutaPdf()
                    )
            );

        }

        Map<Long, CategoriaResumenDTO> categoriasMap = new LinkedHashMap<>();

        if (!temas.isEmpty()) {

            List<TemaPregunta> preguntasTema =
                    temaPreguntaRepository
                            .findByTemaId(
                                    temas.get(0).getId()
                            );

            for (TemaPregunta tp : preguntasTema) {

                Long categoriaId =
                        tp.getPregunta()
                                .getCategoria()
                                .getId();

                String categoriaNombre =
                        tp.getPregunta()
                                .getCategoria()
                                .getNombre();

                if (!categoriasMap.containsKey(categoriaId)) {

                    categoriasMap.put(
                            categoriaId,
                            new CategoriaResumenDTO(
                                    categoriaId,
                                    categoriaNombre,
                                    1
                            )
                    );

                } else {

                    CategoriaResumenDTO dto =
                            categoriasMap.get(
                                    categoriaId
                            );

                    dto.setCantidadPreguntas(
                            dto.getCantidadPreguntas() + 1
                    );

                }

            }

        }

        return new ExamenDetalleDTO(
                examen.getId(),
                examen.getNombre(),
                examen.getProceso().getNombre(),
                examen.getUsuario().getNombre(),
                examen.getFechaGeneracion(),
                examen.getCantidadTemas(),
                new ArrayList<>(
                        categoriasMap.values()
                ),
                temasDTO
        );

    }

}
