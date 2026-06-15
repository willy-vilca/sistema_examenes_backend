package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.categoria.CategoriaRequestDTO;
import com.sistemaexamenes.dto.categoria.CategoriaResponseDTO;
import com.sistemaexamenes.entity.Categoria;
import com.sistemaexamenes.exception.ResourceNotFoundException;
import com.sistemaexamenes.mapper.CategoriaMapper;
import com.sistemaexamenes.repository.CategoriaRepository;
import com.sistemaexamenes.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl
        implements CategoriaService {

    private final CategoriaRepository repository;

    @Override
    public CategoriaResponseDTO crear(
            CategoriaRequestDTO dto) {

        Categoria categoria =
                CategoriaMapper.toEntity(dto);

        repository.findByNombreIgnoreCase(
                        dto.getNombre())
                .ifPresent(c -> {
                    throw new RuntimeException(
                            "Ya existe una categoría con ese nombre");
                });

        categoria = repository.save(categoria);

        return CategoriaMapper.toResponse(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listar() {

        return repository.findAll()
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {

        Categoria categoria =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoría no encontrada"));

        return CategoriaMapper.toResponse(categoria);
    }

    @Override
    public CategoriaResponseDTO actualizar(
            Long id,
            CategoriaRequestDTO dto) {

        Categoria categoria =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoría no encontrada"));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        repository.save(categoria);

        return CategoriaMapper.toResponse(categoria);
    }

    @Override
    public void eliminar(Long id) {

        Categoria categoria =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoría no encontrada"));

        repository.delete(categoria);
    }
}
