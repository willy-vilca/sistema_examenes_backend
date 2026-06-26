package com.sistemaexamenes.controller;

import com.sistemaexamenes.dto.examen.*;
import com.sistemaexamenes.entity.TemaExamen;
import com.sistemaexamenes.repository.TemaExamenRepository;
import com.sistemaexamenes.service.ExamenConsultaService;
import com.sistemaexamenes.service.ExamenService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
public class ExamenController {

    private final ExamenService examenService;
    private final ExamenConsultaService examenConsultaService;
    private final TemaExamenRepository temaExamenRepository;

    @PostMapping("/generar")
    public ResponseEntity<ExamenGeneradoResponseDTO> generarExamen(
            @RequestBody GenerarExamenRequestDTO request
    ) {
        return ResponseEntity.ok(
                examenService.generarExamen(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<ExamenListadoDTO>> listarExamenes() {

        return ResponseEntity.ok(
                examenConsultaService.listarExamenes()
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenDetalleDTO> obtenerDetalle(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                examenConsultaService.obtenerDetalleExamen(id)
        );

    }

    @GetMapping("/proceso/{procesoId}/categorias")
    public ResponseEntity<List<CategoriaDisponibleDTO>>
    obtenerCategoriasDisponibles(
            @PathVariable Long procesoId
    ) {

        return ResponseEntity.ok(
                examenConsultaService
                        .obtenerCategoriasDisponibles(
                                procesoId
                        )
        );

    }

    @GetMapping("/tema/{temaId}/pdf")
    public ResponseEntity<Resource> descargarPdf(
            @PathVariable Long temaId
    ) {
        try {

            TemaExamen tema =
                    temaExamenRepository
                            .findById(temaId)
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Tema no encontrado"
                                    )
                            );

            Path rutaPdf =
                    Paths.get(
                            tema.getRutaPdf()
                    );

            Resource resource =
                    new UrlResource(
                            rutaPdf.toUri()
                    );

            if (!resource.exists()) {
                throw new RuntimeException(
                        "Archivo PDF no encontrado"
                );
            }

            return ResponseEntity.ok()
                    .contentType(
                            MediaType.APPLICATION_PDF
                    )
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\""
                                    + rutaPdf.getFileName()
                                    + "\""
                    )
                    .body(resource);

        }
        catch (Exception e) {
            throw new RuntimeException(
                    "Error al descargar PDF",
                    e
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarExamen(
            @PathVariable Long id
    ) {
        examenService.eliminarExamen(id);
        return ResponseEntity.ok().build();
    }

}
