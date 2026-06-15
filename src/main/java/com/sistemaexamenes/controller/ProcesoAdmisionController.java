package com.sistemaexamenes.controller;

import com.sistemaexamenes.dto.proceso.ProcesoAdmisionRequestDTO;
import com.sistemaexamenes.dto.proceso.ProcesoAdmisionResponseDTO;
import com.sistemaexamenes.service.ProcesoAdmisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procesos")
@RequiredArgsConstructor
public class ProcesoAdmisionController {

    private final ProcesoAdmisionService procesoService;

    @PostMapping
    public ResponseEntity<ProcesoAdmisionResponseDTO>
    crear(
            @Valid
            @RequestBody ProcesoAdmisionRequestDTO dto) {

        return ResponseEntity.ok(
                procesoService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProcesoAdmisionResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                procesoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcesoAdmisionResponseDTO>
    buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                procesoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcesoAdmisionResponseDTO>
    actualizar(
            @PathVariable Long id,
            @Valid
            @RequestBody ProcesoAdmisionRequestDTO dto) {

        return ResponseEntity.ok(
                procesoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminar(
            @PathVariable Long id) {

        procesoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}