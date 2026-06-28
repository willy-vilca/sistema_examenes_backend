package com.sistemaexamenes.controller;

import com.sistemaexamenes.dto.pregunta.PreguntaRequestDTO;
import com.sistemaexamenes.dto.pregunta.PreguntaResponseDTO;
import com.sistemaexamenes.dto.pregunta.ReplicarPreguntasRequestDTO;
import com.sistemaexamenes.dto.pregunta.ReplicarPreguntasResponseDTO;
import com.sistemaexamenes.service.PreguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
public class PreguntaController {

    private final PreguntaService preguntaService;

    @PostMapping
    public ResponseEntity<PreguntaResponseDTO>
    crear(
            @Valid
            @RequestBody
            PreguntaRequestDTO dto) {

        return ResponseEntity.ok(
                preguntaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreguntaResponseDTO>
    actualizar(
            @PathVariable Long id,
            @Valid
            @RequestBody
            PreguntaRequestDTO dto) {

        return ResponseEntity.ok(
                preguntaService.actualizar(
                        id,
                        dto
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<PreguntaResponseDTO>>
    listar() {

        return ResponseEntity.ok(
                preguntaService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreguntaResponseDTO>
    buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                preguntaService.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminar(
            @PathVariable Long id) {

        preguntaService.eliminar(id);

        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void>
    cambiarEstado(
            @PathVariable Long id) {

        preguntaService.cambiarEstado(id);

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/replicar")
    public ResponseEntity<ReplicarPreguntasResponseDTO>
    replicarPreguntas(
            @RequestBody
            ReplicarPreguntasRequestDTO dto
    ) {

        return ResponseEntity.ok(
                preguntaService.replicarPreguntas(dto)
        );

    }
}
