package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.categoriaPadre.CategoriaPadreRequestDTO;
import com.sistemaexamenes.dto.categoriaPadre.CategoriaPadreResponseDTO;
import com.sistemaexamenes.entity.CategoriaPadre;
import com.sistemaexamenes.repository.CategoriaPadreRepository;
import com.sistemaexamenes.service.CategoriaPadreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaPadreServiceImpl implements CategoriaPadreService {

    private final CategoriaPadreRepository categoriaPadreRepository;

    @Override
    public List<CategoriaPadreResponseDTO> listar() {

        return categoriaPadreRepository.findAll()
                .stream()
                .map(c -> new CategoriaPadreResponseDTO(
                        c.getId(),
                        c.getNombre(),
                        c.getDescripcion()
                ))
                .toList();

    }

    @Override
    public CategoriaPadreResponseDTO obtenerPorId(Long id) {

        CategoriaPadre categoria = categoriaPadreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));

        return new CategoriaPadreResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );

    }

    @Override
    public CategoriaPadreResponseDTO crear(CategoriaPadreRequestDTO dto) {

        categoriaPadreRepository.findByNombreIgnoreCase(dto.getNombre())
                .ifPresent(c -> {
                    throw new RuntimeException("Ya existe una categoría padre con ese nombre");
                });

        CategoriaPadre categoria = new CategoriaPadre();

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        categoria = categoriaPadreRepository.save(categoria);

        return new CategoriaPadreResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );

    }

    @Override
    public CategoriaPadreResponseDTO actualizar(Long id, CategoriaPadreRequestDTO dto) {

        CategoriaPadre categoria = categoriaPadreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        categoria = categoriaPadreRepository.save(categoria);

        return new CategoriaPadreResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );

    }

    @Override
    public void eliminar(Long id) {

        categoriaPadreRepository.deleteById(id);

    }

}
