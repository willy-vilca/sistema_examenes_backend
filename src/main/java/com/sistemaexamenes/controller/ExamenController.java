package com.sistemaexamenes.controller;

import com.sistemaexamenes.dto.examen.ExamenGeneradoResponseDTO;
import com.sistemaexamenes.dto.examen.GenerarExamenRequestDTO;

import com.sistemaexamenes.service.ExamenService;
import com.sistemaexamenes.service.FormulaImageService;
import com.sistemaexamenes.service.PdfGeneratorService;
import com.sistemaexamenes.repository.TemaExamenRepository;
import com.sistemaexamenes.entity.TemaExamen;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
public class ExamenController {
    private final ExamenService examenService;
    private final PdfGeneratorService pdfGeneratorService;
    private final TemaExamenRepository temaExamenRepository;
    private final FormulaImageService formulaImageService;

    @PostMapping("/generar")
    public ResponseEntity<ExamenGeneradoResponseDTO> generarExamen(
            @RequestBody
            GenerarExamenRequestDTO request
    ) {
        return ResponseEntity.ok(
                examenService
                        .generarExamen(
                                request
                        )
        );
    }

    @GetMapping("/test-pdf/{temaId}")
    public ResponseEntity<String> testPdf(
            @PathVariable Long temaId
    ) {

        TemaExamen tema =

                temaExamenRepository
                        .findById(temaId)
                        .orElseThrow();

        String ruta =

                pdfGeneratorService
                        .generarPdfTema(
                                tema
                        );

        return ResponseEntity.ok(
                ruta
        );
    }

    @GetMapping("/test-formula")
    public String testFormula() {

        return formulaImageService
                .generarImagenFormula(
                        "x^2+y^2=z^2"
                );
    }
}
