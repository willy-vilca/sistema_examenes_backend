package com.sistemaexamenes.mapper;

import com.sistemaexamenes.dto.categoria.CategoriaRequestDTO;
import com.sistemaexamenes.dto.categoria.CategoriaResponseDTO;
import com.sistemaexamenes.entity.Categoria;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        return categoria;
    }

    public static CategoriaResponseDTO toResponse(Categoria categoria) {

        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }
}