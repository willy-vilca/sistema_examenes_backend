package com.sistemaexamenes.controller;

import com.sistemaexamenes.dto.categoriaPadre.CategoriaPadreRequestDTO;
import com.sistemaexamenes.dto.categoriaPadre.CategoriaPadreResponseDTO;
import com.sistemaexamenes.service.CategoriaPadreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-padre")
@RequiredArgsConstructor
public class CategoriaPadreController {

    private final CategoriaPadreService categoriaPadreService;

    @GetMapping
    public ResponseEntity<List<CategoriaPadreResponseDTO>> listar() {
        return ResponseEntity.ok(categoriaPadreService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaPadreResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaPadreService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaPadreResponseDTO> crear(@RequestBody CategoriaPadreRequestDTO dto) {
        return ResponseEntity.ok(categoriaPadreService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaPadreResponseDTO> actualizar(@PathVariable Long id,
                                                                @RequestBody CategoriaPadreRequestDTO dto) {
        return ResponseEntity.ok(categoriaPadreService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaPadreService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
