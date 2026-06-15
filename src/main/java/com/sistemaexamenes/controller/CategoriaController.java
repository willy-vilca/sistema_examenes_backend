package com.sistemaexamenes.controller;

import com.sistemaexamenes.dto.categoria.CategoriaRequestDTO;
import com.sistemaexamenes.dto.categoria.CategoriaResponseDTO;
import com.sistemaexamenes.entity.Categoria;
import com.sistemaexamenes.repository.CategoriaRepository;
import com.sistemaexamenes.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO>
    crear(
            @Valid
            @RequestBody CategoriaRequestDTO dto) {

        return ResponseEntity.ok(
                categoriaService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                categoriaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO>
    buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(
                categoriaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO>
    actualizar(
            @PathVariable Long id,
            @Valid
            @RequestBody CategoriaRequestDTO dto) {

        return ResponseEntity.ok(
                categoriaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminar(@PathVariable Long id) {

        categoriaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
