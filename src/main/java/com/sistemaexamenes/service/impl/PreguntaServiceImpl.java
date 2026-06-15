package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.pregunta.AlternativaRequestDTO;
import com.sistemaexamenes.dto.pregunta.PreguntaRequestDTO;
import com.sistemaexamenes.dto.pregunta.PreguntaResponseDTO;
import com.sistemaexamenes.entity.*;
import com.sistemaexamenes.exception.ResourceNotFoundException;
import com.sistemaexamenes.mapper.PreguntaMapper;
import com.sistemaexamenes.repository.*;
import com.sistemaexamenes.service.PreguntaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PreguntaServiceImpl implements PreguntaService {
    private final PreguntaRepository preguntaRepository;

    private final ProcesoAdmisionRepository procesoRepository;

    private final CategoriaRepository categoriaRepository;

    private final UsuarioRepository usuarioRepository;

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

        Usuario usuario =
                usuarioRepository.findById(1L)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Usuario no encontrado"));

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

        pregunta.setFechaActualizacion(
                LocalDateTime.now());

        pregunta.getAlternativas().clear();

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
}
