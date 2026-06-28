package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.pregunta.AlternativaRequestDTO;
import com.sistemaexamenes.dto.pregunta.PreguntaRequestDTO;
import com.sistemaexamenes.dto.pregunta.PreguntaResponseDTO;
import com.sistemaexamenes.dto.pregunta.ReplicarPreguntasRequestDTO;
import com.sistemaexamenes.dto.pregunta.ReplicarPreguntasResponseDTO;
import com.sistemaexamenes.entity.*;
import com.sistemaexamenes.exception.ResourceNotFoundException;
import com.sistemaexamenes.mapper.PreguntaMapper;
import com.sistemaexamenes.repository.*;
import com.sistemaexamenes.service.PreguntaService;
import com.sistemaexamenes.service.SesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PreguntaServiceImpl implements PreguntaService {
    private final PreguntaRepository preguntaRepository;

    private final ProcesoAdmisionRepository procesoRepository;

    private final CategoriaRepository categoriaRepository;

    private final UsuarioRepository usuarioRepository;

    private final SesionService sesionService;

    private void validarAlternativas(
            List<AlternativaRequestDTO> alternativas) {

        if (alternativas == null ||
                alternativas.size() < 2) {

            throw new RuntimeException(
                    "La pregunta debe tener al menos 2 alternativas");
        }

        long correctas =
                alternativas.stream()
                        .filter(
                                AlternativaRequestDTO::getEsCorrecta)
                        .count();

        if (correctas != 1) {

            throw new RuntimeException(
                    "Debe existir una única respuesta correcta");
        }
    }

    @Override
    public PreguntaResponseDTO crear(
            PreguntaRequestDTO dto) {

        validarAlternativas(
                dto.getAlternativas());

        ProcesoAdmision proceso =
                procesoRepository.findById(
                                dto.getProcesoId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Proceso no encontrado"));

        Categoria categoria =
                categoriaRepository.findById(
                                dto.getCategoriaId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoría no encontrada"));

        Usuario usuario = sesionService.obtenerUsuarioActual();

        Pregunta pregunta = new Pregunta();

        pregunta.setProceso(proceso);
        pregunta.setCategoria(categoria);
        pregunta.setUsuario(usuario);
        pregunta.setContenidoHtml(
                dto.getContenidoHtml());
        pregunta.setActivo(true);
        pregunta.setFechaCreacion(
                LocalDateTime.now());
        pregunta.setFechaActualizacion(
                LocalDateTime.now());

        Pregunta finalPregunta = pregunta;
        dto.getAlternativas()
                .forEach(aDto -> {

                    Alternativa alternativa =
                            new Alternativa();

                    alternativa.setPregunta(
                            finalPregunta);

                    alternativa.setContenidoHtml(
                            aDto.getContenidoHtml());

                    alternativa.setEsCorrecta(
                            aDto.getEsCorrecta());

                    finalPregunta.getAlternativas()
                            .add(alternativa);
                });

        pregunta =
                preguntaRepository.save(
                        pregunta);

        return PreguntaMapper.toResponse(
                pregunta);
    }

    @Override
    public PreguntaResponseDTO actualizar(
            Long id,
            PreguntaRequestDTO dto) {

        validarAlternativas(
                dto.getAlternativas());

        Pregunta pregunta =
                preguntaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Pregunta no encontrada"));

        ProcesoAdmision proceso =
                procesoRepository.findById(
                                dto.getProcesoId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Proceso no encontrado"));

        Categoria categoria =
                categoriaRepository.findById(
                                dto.getCategoriaId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoría no encontrada"));

        pregunta.setProceso(proceso);
        pregunta.setCategoria(categoria);
        pregunta.setContenidoHtml(
                dto.getContenidoHtml());

        pregunta.setFechaActualizacion(LocalDateTime.now());

        Map<Long, Alternativa> alternativasExistentes =
                pregunta.getAlternativas()
                        .stream()
                        .collect(Collectors.toMap(
                                Alternativa::getId,
                                Function.identity()
                        ));

        for (AlternativaRequestDTO alternativaDTO : dto.getAlternativas()) {

            Alternativa alternativa = alternativasExistentes.get(alternativaDTO.getId());

            if (alternativa == null) {
                throw new ResourceNotFoundException(
                        "Alternativa no encontrada: " + alternativaDTO.getId()
                );
            }

            alternativa.setContenidoHtml(
                    alternativaDTO.getContenidoHtml()
            );

            alternativa.setEsCorrecta(
                    alternativaDTO.getEsCorrecta()
            );

        }

        pregunta = preguntaRepository.save(pregunta);

        return PreguntaMapper.toResponse(pregunta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaResponseDTO> listar() {

        return preguntaRepository.findAll()
                .stream()
                .map(PreguntaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PreguntaResponseDTO buscarPorId(
            Long id) {

        Pregunta pregunta =
                preguntaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Pregunta no encontrada"));

        return PreguntaMapper.toResponse(
                pregunta);
    }

    @Override
    public void eliminar(Long id) {

        Pregunta pregunta =
                preguntaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Pregunta no encontrada"));

        preguntaRepository.delete(
                pregunta);
    }

    @Override
    public void cambiarEstado(Long id) {

        Pregunta pregunta =
                preguntaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Pregunta no encontrada"));

        pregunta.setActivo(
                !pregunta.getActivo());

        preguntaRepository.save(
                pregunta);
    }

    @Override
    public ReplicarPreguntasResponseDTO replicarPreguntas(
            ReplicarPreguntasRequestDTO dto
    ) {

        ProcesoAdmision procesoDestino =
                procesoRepository.findById(
                        dto.getProcesoDestinoId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Proceso destino no encontrado"
                        )
                );

        Usuario usuario = sesionService.obtenerUsuarioActual();

        List<Pregunta> preguntas =
                preguntaRepository.findAllById(
                        dto.getPreguntasIds()
                );

        int copiadas = 0;
        int omitidas = 0;

        Set<Long> preguntasYaReplicadas = new HashSet<>();

        for (Pregunta preguntaOriginal : preguntas) {

            if (preguntaOriginal
                    .getProceso()
                    .getId()
                    .equals(procesoDestino.getId())) {

                omitidas++;
                continue;
            }

            if (!preguntasYaReplicadas.add(
                    preguntaOriginal.getId())) {

                continue;
            }

            Pregunta nuevaPregunta = new Pregunta();

            nuevaPregunta.setProceso(
                    procesoDestino
            );

            nuevaPregunta.setCategoria(
                    preguntaOriginal.getCategoria()
            );

            nuevaPregunta.setUsuario(
                    usuario
            );

            nuevaPregunta.setContenidoHtml(
                    preguntaOriginal.getContenidoHtml()
            );

            nuevaPregunta.setActivo(
                    preguntaOriginal.getActivo()
            );

            nuevaPregunta.setFechaCreacion(
                    LocalDateTime.now()
            );

            nuevaPregunta.setFechaActualizacion(
                    LocalDateTime.now()
            );

            for (Alternativa alternativaOriginal :
                    preguntaOriginal.getAlternativas()) {

                Alternativa nuevaAlternativa = new Alternativa();

                nuevaAlternativa.setPregunta(
                        nuevaPregunta
                );

                nuevaAlternativa.setContenidoHtml(
                        alternativaOriginal.getContenidoHtml()
                );

                nuevaAlternativa.setEsCorrecta(
                        alternativaOriginal.getEsCorrecta()
                );

                nuevaPregunta
                        .getAlternativas()
                        .add(nuevaAlternativa);

            }

            preguntaRepository.save(
                    nuevaPregunta
            );

            copiadas++;

        }

        return ReplicarPreguntasResponseDTO
                .builder()
                .preguntasCopiadas(
                        copiadas
                )
                .preguntasOmitidas(
                        omitidas
                )
                .build();

    }
}
