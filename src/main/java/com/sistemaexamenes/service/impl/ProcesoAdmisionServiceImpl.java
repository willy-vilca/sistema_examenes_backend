package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.proceso.ProcesoAdmisionRequestDTO;
import com.sistemaexamenes.dto.proceso.ProcesoAdmisionResponseDTO;
import com.sistemaexamenes.entity.ProcesoAdmision;
import com.sistemaexamenes.exception.ResourceNotFoundException;
import com.sistemaexamenes.mapper.ProcesoAdmisionMapper;
import com.sistemaexamenes.repository.ProcesoAdmisionRepository;
import com.sistemaexamenes.service.ProcesoAdmisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProcesoAdmisionServiceImpl
        implements ProcesoAdmisionService {

    private final ProcesoAdmisionRepository repository;

    @Override
    public ProcesoAdmisionResponseDTO crear(
            ProcesoAdmisionRequestDTO dto) {

        ProcesoAdmision proceso =
                ProcesoAdmisionMapper.toEntity(dto);

        proceso.setFecha_creacion(LocalDateTime.now());

        proceso = repository.save(proceso);

        return ProcesoAdmisionMapper.toResponse(proceso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcesoAdmisionResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(ProcesoAdmisionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProcesoAdmisionResponseDTO buscarPorId(
            Long id) {

        ProcesoAdmision proceso =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Proceso de admisión no encontrado"));

        return ProcesoAdmisionMapper.toResponse(proceso);
    }

    @Override
    public ProcesoAdmisionResponseDTO actualizar(
            Long id,
            ProcesoAdmisionRequestDTO dto) {

        ProcesoAdmision proceso =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Proceso de admisión no encontrado"));

        proceso.setNombre(dto.getNombre());
        proceso.setDescripcion(dto.getDescripcion());
        proceso.setFecha_inicio(dto.getFecha_inicio());
        proceso.setFecha_fin(dto.getFecha_fin());
        proceso.setEstado(dto.getEstado());

        repository.save(proceso);

        return ProcesoAdmisionMapper.toResponse(proceso);
    }

    @Override
    public void eliminar(Long id) {

        ProcesoAdmision proceso =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Proceso de admisión no encontrado"));

        repository.delete(proceso);
    }
}
