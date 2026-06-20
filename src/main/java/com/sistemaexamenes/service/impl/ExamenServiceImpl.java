package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.examen.ExamenGeneradoResponseDTO;
import com.sistemaexamenes.dto.examen.GenerarExamenRequestDTO;
import com.sistemaexamenes.service.PdfGeneratorService;
import com.sistemaexamenes.service.FormulaImageService;
import com.sistemaexamenes.entity.*;
import com.sistemaexamenes.repository.*;
import com.sistemaexamenes.service.ExamenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.sistemaexamenes.dto.examen.CategoriaSeleccionadaDTO;
import com.sistemaexamenes.dto.examen.PreguntasSeleccionadasDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamenServiceImpl
        implements ExamenService {
    private final ExamenGeneradoRepository examenGeneradoRepository;
    private final ExamenCategoriaRepository examenCategoriaRepository;
    private final TemaExamenRepository temaExamenRepository;
    private final TemaPreguntaRepository temaPreguntaRepository;
    private final TemaAlternativaRepository temaAlternativaRepository;
    private final PreguntaRepository preguntaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProcesoAdmisionRepository procesoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PdfGeneratorService pdfGeneratorService;
    private final FormulaImageService formulaImageService;

    @Override
    public ExamenGeneradoResponseDTO
    generarExamen(
            GenerarExamenRequestDTO request
    ) {

        ExamenGenerado examen = crearExamenBase(request);

        List<PreguntasSeleccionadasDTO> preguntasSeleccionadas =
                guardarCategoriasYSeleccionarPreguntas(
                        examen,
                        request
                );

        generarTemas(examen, preguntasSeleccionadas);

        formulaImageService.eliminarFormulasTemporales();

        return ExamenGeneradoResponseDTO
                .builder()
                .id(examen.getId())
                .nombre(examen.getNombre())
                .build();
    }

    private ExamenGenerado
    crearExamenBase(
            GenerarExamenRequestDTO request
    ) {
        ProcesoAdmision proceso =
                procesoRepository.findById(
                                request.getProcesoId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Proceso no encontrado"
                                )
                        );

        Usuario usuario =
                usuarioRepository.findById(
                                1L
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Usuario no encontrado"
                                )
                        );

        ExamenGenerado examen =
                new ExamenGenerado();

        examen.setProceso(
                proceso
        );

        examen.setUsuario(
                usuario
        );

        examen.setNombre(
                request.getNombreExamen()
        );

        examen.setCantidadTemas(
                request.getCantidadTemas()
        );

        examen.setFechaGeneracion(
                LocalDateTime.now()
        );

        return examenGeneradoRepository
                .save(examen);
    }

    private List<PreguntasSeleccionadasDTO>
    guardarCategoriasYSeleccionarPreguntas(
            ExamenGenerado examen,
            GenerarExamenRequestDTO request
    ) {
        List<PreguntasSeleccionadasDTO> resultado = new ArrayList<>();

        for (CategoriaSeleccionadaDTO categoriaDTO : request.getCategorias()) {
            if (
                    categoriaDTO.getCantidadPreguntas() <= 0
            ) {
                continue;
            }

            Categoria categoria = categoriaRepository
                                .findById(
                                        categoriaDTO.getCategoriaId()
                                )
                                .orElseThrow(
                                        () -> new RuntimeException("Categoría no encontrada")
                                );

            Long disponibles = preguntaRepository
                                .countByProcesoIdAndCategoriaIdAndActivoTrue(
                                        request.getProcesoId(),
                                        categoria.getId()
                                );

            if (categoriaDTO.getCantidadPreguntas() > disponibles) {
                throw new RuntimeException(
                        "La categoría "
                                + categoria.getNombre()
                                + " solo tiene "
                                + disponibles
                                + " preguntas disponibles."
                );
            }

            ExamenCategoria examenCategoria = new ExamenCategoria();

            examenCategoria.setExamenGenerado(
                    examen
            );
            examenCategoria.setCategoria(
                    categoria
            );
            examenCategoria.setCantidadPreguntas(
                    categoriaDTO.getCantidadPreguntas()
            );
            examenCategoriaRepository.save(
                    examenCategoria
            );

            List<Pregunta> preguntas =
                    preguntaRepository
                            .findByProcesoIdAndCategoriaIdAndActivoTrue(
                                    request.getProcesoId(),
                                    categoria.getId()
                            );

            Collections.shuffle(preguntas);
            List<Pregunta> seleccionadas =
                        preguntas.subList(
                                0,
                                categoriaDTO.getCantidadPreguntas()
                        );

            resultado.add(
                    new PreguntasSeleccionadasDTO(
                            categoria.getId(),
                            categoria.getNombre(),
                            seleccionadas
                    )
            );
        }

        return resultado;
    }

    private void generarTemas(
            ExamenGenerado examen,
            List<PreguntasSeleccionadasDTO> preguntasSeleccionadas
    ) {
        for (int i = 0; i < examen.getCantidadTemas(); i++) {
            TemaExamen tema = new TemaExamen();

            tema.setExamenGenerado(
                    examen
            );

            tema.setCodigoTema(
                    generarCodigoTema(i)
            );

            tema.setFechaCreacion(
                    LocalDateTime.now()
            );

            tema = temaExamenRepository.save(
                            tema
                    );

            generarPreguntasTema(tema, preguntasSeleccionadas);

            String rutaPdf = pdfGeneratorService.generarPdfTema(tema);

            tema.setRutaPdf(rutaPdf);

            temaExamenRepository.save(tema);
        }
    }

    private String generarCodigoTema(
            int indice
    ) {
        return String.valueOf(
                (char) ('A' + indice)
        );
    }

    private void generarPreguntasTema(
            TemaExamen tema,
            List<PreguntasSeleccionadasDTO> categorias
    ) {
        int ordenGlobal = 1;

        for (PreguntasSeleccionadasDTO categoria : categorias) {
            List<Pregunta> preguntas =
                    new ArrayList<>(
                            categoria.getPreguntas()
                    );

            Collections.shuffle(preguntas);

            for (Pregunta pregunta : preguntas) {
                TemaPregunta temaPregunta = new TemaPregunta();
                temaPregunta.setTema(
                        tema
                );
                temaPregunta.setPregunta(
                        pregunta
                );
                temaPregunta.setOrdenPregunta(
                        ordenGlobal++
                );
                temaPregunta = temaPreguntaRepository.save(
                                temaPregunta
                            );

                generarAlternativasTema(
                        temaPregunta,
                        pregunta
                );
            }
        }
    }

    private void generarAlternativasTema(
            TemaPregunta temaPregunta,
            Pregunta pregunta
    ) {
        List<Alternativa> alternativas =
                new ArrayList<>(
                        pregunta.getAlternativas()
                );
        Collections.shuffle(alternativas);

        int orden = 1;

        for (Alternativa alternativa : alternativas) {
            TemaAlternativa temaAlternativa = new TemaAlternativa();
            temaAlternativa.setTemaPregunta(
                    temaPregunta
            );
            temaAlternativa.setAlternativa(
                    alternativa
            );
            temaAlternativa.setOrdenAlternativa(
                    orden++
            );
            temaAlternativaRepository.save(
                    temaAlternativa
            );
        }
    }
}
